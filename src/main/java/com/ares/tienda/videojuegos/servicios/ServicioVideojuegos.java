package com.ares.tienda.videojuegos.servicios;

import com.ares.tienda.videojuegos.model.Videojuego;
import jakarta.validation.Valid;

import java.util.List;

public interface ServicioVideojuegos {

    void registrarVideojuego(Videojuego v);

    List<Videojuego> obtenerVideojuegos();

    Videojuego obtenerVideojuegoPorId(long id);

    void borrarVideojuego(long id) throws Exception;

    void guardarCambiosVideojuego(Videojuego videojuegoEditar) throws Exception;
}
