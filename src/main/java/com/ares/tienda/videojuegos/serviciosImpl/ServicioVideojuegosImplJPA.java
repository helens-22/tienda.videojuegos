package com.ares.tienda.videojuegos.serviciosImpl;

import com.ares.tienda.videojuegos.model.Videojuego;
import com.ares.tienda.videojuegos.servicios.ServicioVideojuegos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service //marcas que la clase actua como servicio de datos
@Transactional // los metodos de la clase se ejecutan como una unica transaccion
// Transaccion:
public class ServicioVideojuegosImplJPA implements ServicioVideojuegos {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrarVideojuego(Videojuego v) {
        //el videojuego ahora mismo tiene la fotoSubida, pero
        // en imagenPortada no tiene nada.
        try {
            v.setImagenPortada(v.getFotoSubida().getBytes());
        } catch (IOException e) {
            System.out.println("NO PUDE PROCESAR LA FOTO SUBIDA");
            throw new RuntimeException(e);
        }
        entityManager.persist(v);
    }

    @Override
    public List<Videojuego> obtenerVideojuegos() {
        // lanzar JPQL
        // la consulta lanzada equivale al sql: select * from tabala_videojuegos as v
        return entityManager.createQuery("select v from Videojuego v").getResultList();
    }

    @Override
    public Videojuego obtenerVideojuegoPorId(long id) {
        return entityManager.find(Videojuego.class, id);
    }

    @Override
    public void borrarVideojuego(long id) throws Exception {
        // En JPA la forma mas comun de borrar un registro,
        // Es pedirlo, comprobar que existe y luego borrarlo
        // id = -3; para probar lanzar/throw Exception
        Videojuego videojuegoAborrar = entityManager.find(Videojuego.class, id);
        if( videojuegoAborrar != null) {
            entityManager.remove(videojuegoAborrar);
        }else {
            throw new Exception("id no encontrada");
        }
    }

    @Override
    public void guardarCambiosVideojuego(Videojuego videojuegoEditar) throws Exception {
        // para no perder la foto hay que detectar si el admin le ha asignado
        // al videojuego una nueva foto, en caso contrario
        // le vamos a cargar la foto anterio que tuviese
        if(videojuegoEditar.getFotoSubida().getSize() == 0){
            Videojuego original =
                    entityManager.find(Videojuego.class, videojuegoEditar.getId());
            videojuegoEditar.setImagenPortada(original.getImagenPortada());
        } else{
            videojuegoEditar.setImagenPortada(
                    videojuegoEditar.getFotoSubida().getBytes()
            );
        }
        // merge actualiza el registro si ya existe
        // si no existe lo crea
        entityManager.merge(videojuegoEditar);
    }
}
