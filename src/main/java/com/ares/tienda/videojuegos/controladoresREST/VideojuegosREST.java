package com.ares.tienda.videojuegos.controladoresREST;

import com.ares.tienda.videojuegos.model.Videojuego;
import com.ares.tienda.videojuegos.servicios.ServicioVideojuegos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Un servicio REST
//se le puede llamar tambien API
//esta archivo seria el API de videojuegos

//@RestController no devuelven vistas/htmls sino json
@RestController
@RequestMapping("restVideojuegos/")
public class VideojuegosREST {

    @Autowired
    private ServicioVideojuegos servicioVideojuegos;

    @RequestMapping("listar")
    public List<Videojuego> listar(){
        return servicioVideojuegos.obtenerVideojuegos();
    }

    //para testear:
//   http://localhost:8080/restVideojuegos/obtener-videojuego?id=1
    @RequestMapping("obtener-videojuego")
    public Videojuego obtenerVideojuego(@RequestParam("id") Long id){
        //estos return de un @RestController devuelven json
        return servicioVideojuegos.obtenerVideojuegoPorId(id);
    }

}
