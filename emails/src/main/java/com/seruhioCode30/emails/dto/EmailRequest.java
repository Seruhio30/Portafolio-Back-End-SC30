package com.seruhioCode30.emails.dto;

public class EmailRequest {

    private String remitente;

    private String nombre;

    private String telefono;

    private String categoria;

    private String contenido;

    private String url;

    private String ip;

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
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}