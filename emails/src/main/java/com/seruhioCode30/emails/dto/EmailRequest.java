package com.seruhioCode30.emails.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmailRequest {

    @NotBlank
    @Email
    @Size(max = 254)
    private String remitente;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 40)
    private String telefono;

    @Size(max = 50)
    @Pattern(
            regexp = "^$|pagina-web|aplicacion-web|full-stack|backend|colaboracion|oportunidad-profesional|otros$"
    )
    private String categoria;

    @NotBlank
    @Size(max = 5000)
    private String contenido;

    @NotBlank
    @Size(max = 2048)
    private String url;

    public EmailRequest() {}

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
