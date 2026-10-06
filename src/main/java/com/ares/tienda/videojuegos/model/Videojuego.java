package com.ares.tienda.videojuegos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "tabla_videojuegos")
public class Videojuego {

    //una forma muy comoda e interesente para hacer validaciones
    // es usar un standar que se llama JSR 303
    // se basa en poner anotaciones de validacion a cada campo

    @Size(min = 3, max = 40, message = "titulo debe tener entre 3 y 40 caracteres")
    @NotEmpty(message = "debes insertar un titulo")
    // una expresion regular es una forma de indicar
    // que queremos o no en un campo de entrada de usuario
    @Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÑüÜ 0-9]+",
            message = "titulo solo puede tener letras numeros y espacios")
    private String titulo;

    @Min(value = 1, message = "precio minimo 1 euro")
    @Max(value = 99999, message = "el precio maximo es 99999 euros")
    private double precio;

    @NotNull(message = "debes indicar un precio")
    private String plataforma;
    private String descripcion;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //este campo va a ser solo
    //gestiar la subida de archivo, el input type="file"
    // esto es solo para recibir facilmente la imagen del form
    // @Transient es para decir que el campo sea ignorado de cara a la base de datos
    @Transient
    private MultipartFile fotoSubida;

    //el siguiente campo es para almacenar la imagen
    //en base de datos
    @Lob // para tipos de columna, p.ej blob o clob, para guardar imagenes/archivos/arrays de bytes
    private byte[] imagenPortada;

    public Videojuego() {
    }

    public Videojuego(String titulo, double precio, String plataforma, String descripcion) {
        this.titulo = titulo;
        this.precio = precio;
        this.plataforma = plataforma;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MultipartFile getFotoSubida() {
        return fotoSubida;
    }

    public void setFotoSubida(MultipartFile fotoSubida) {
        this.fotoSubida = fotoSubida;
    }

    public byte[] getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(byte[] imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "titulo='" + titulo + '\'' +
                ", precio=" + precio +
                ", plataforma='" + plataforma + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}
