package com.ares.tienda.videojuegos.controllers;

import com.ares.tienda.videojuegos.model.Videojuego;
import com.ares.tienda.videojuegos.servicios.ServicioVideojuegos;
import com.ares.tienda.videojuegos.serviciosImpl.ServicioVideojuegosImplJPA;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/")
public class ControladorVideojuegos {

    //pido a spring que me de el objeto que tiene de la unica clase que tiene el implements de ServicioVideojuegos
    //dicho objeto, es el objeto de la clase que tiene @Service class ServicioVideojuegosImplJPA implements ServicioVideojuegos
    @Autowired
    private ServicioVideojuegos servicioVideojuegos;

    @RequestMapping("gestionarVideojuegos")
    public String gestionarVideojuegos(Model model){
        model.addAttribute("videojuegos",servicioVideojuegos.obtenerVideojuegos());
        return "admin/gestionar_productos";
    }

    @RequestMapping("registrarVideojuego")
    public String registrarVideojuego(Model model){
        Videojuego nuevo = new Videojuego();
        nuevo.setPrecio(1);
        model.addAttribute("nuevo", nuevo);
        return "admin/registro_producto";
    }

    @RequestMapping("guardarVideojuego")
    public String guardarVideojuego(
            @ModelAttribute("nuevo")
            @Valid
            Videojuego nuevoVideojuego,
            BindingResult resultadoValidacion,
            Model model ){
        System.out.println("recibido para registrar: " + nuevoVideojuego);
        if( resultadoValidacion.hasErrors()){
            // si el videojuego tiene errores, mandamos al usuario a
            // el formulario de nuevo
            return "admin/registro_producto";
        }
        servicioVideojuegos.registrarVideojuego(nuevoVideojuego);
        return "admin/registro_producto_ok";
    }

    @RequestMapping("borrarVideojuego")
    public String borrarVideojuego(@RequestParam("id") Long id, Model model) throws Exception {
        //repaso: model es una variable donde meto lo que quiera que le llegue
        //a la vista indicada en el return
        System.out.println("id recibida para borrar registro: " + id);
        servicioVideojuegos.borrarVideojuego(id);
        return gestionarVideojuegos(model);
    }

    @RequestMapping("editarVideojuego")
    public String editarVideojuego(@RequestParam("id") Long id, Model model){
        model.addAttribute("videojuegoEditar",
                servicioVideojuegos.obtenerVideojuegoPorId(id));
        return "admin/editar_producto";
    }

    //el metodo va a atender la ruta guardarCambiosVideojuego
    // a esa ruta se llega al hacer click en el GUARDAR CAMBIOS del form de edicion
    @RequestMapping("guardarCambiosVideojuego")
    public String guardarCambiosVideojuego(
            @ModelAttribute("videojuegoEditar") //th:object del form
            @Valid //para validar con las reglas puestas con @ en Videjuego.java
            Videojuego videojuegoEditar, //objeto que contiene lo introducido en el form
            BindingResult resultadoValidacion,
            Model model ) throws Exception { //model en estos metodos, es para meterle lo que queramos que le llegue a la vista devuelta en el return
        if( resultadoValidacion.hasErrors()){
            return "admin/editar_producto";
        }else{
            servicioVideojuegos.guardarCambiosVideojuego(videojuegoEditar);
            return gestionarVideojuegos(model);
        }
    }


}//end class
