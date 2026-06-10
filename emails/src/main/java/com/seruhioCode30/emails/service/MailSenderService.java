package com.seruhioCode30.emails.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.seruhioCode30.emails.dto.EmailRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailSenderService {

    @Value("${resend.api.key}")
    private String apiKey;
    public void enviarCorreo(EmailRequest emailRequest) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.resend.com/emails";

        Map<String, Object> body = new HashMap<>();
        body.put("from", "SeruhioCode30 <onboarding@resend.dev>");
        body.put("to", List.of("seruhiocode30@gmail.com"));
        body.put("subject", "Nuevo mensaje del portafolio");

        // ✅ FECHA/HORA Costa Rica
        String fecha = LocalDateTime.now(
                java.time.ZoneId.of("America/Costa_Rica")
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String html =
                "<div style=\"font-family: Arial, sans-serif; padding: 20px; background: #F6F6F6; color: #000000;\">" +
                        "<h2 style=\"color: #1158FC; border-bottom: 2px solid #22D4FD; padding-bottom: 4px;\">Nuevo mensaje del portafolio</h2>" +

                        "<p><strong>Nombre:</strong> " + emailRequest.getNombre() + "</p>" +
                        "<p><strong>Email:</strong> " + emailRequest.getRemitente() + "</p>" +
                        "<p><strong>Teléfono:</strong> " + emailRequest.getTelefono() + "</p>" +
                        "<p><strong>Categoría:</strong> " + emailRequest.getCategoria() + "</p>" +
                        "<p><strong>Fecha/Hora:</strong> " + fecha + "</p>" +
                        "<p><strong>IP:</strong> " + emailRequest.getIp() + "</p>" +
                        "<p><strong>URL:</strong> <a href=\"" + emailRequest.getUrl() + "\">" + emailRequest.getUrl() + "</a></p>" +

                        "<div style=\"margin-top: 20px; padding: 15px; background: #ffffff; border-left: 4px solid #22D4FD;\">" +
                        "<p style=\"margin: 0;\"><strong>Mensaje:</strong></p>" +
                        "<p style=\"white-space: pre-wrap;\">" + emailRequest.getContenido() + "</p>" +
                        "</div>" +

                        "<div style=\"margin-top: 20px;\">" +
                        "<a href=\"mailto:" + emailRequest.getRemitente() + "\" " +
                        "style=\"padding:10px 15px; background:#22D4FD; color:#000000; text-decoration:none; margin-right:10px;\">" +
                        "Responder" +
                        "</a>" +

                        "<a href=\"https://wa.me/50687733663\" " +
                        "style=\"padding:10px 15px; background:#1158FC; color:white; text-decoration:none;\">" +
                        "WhatsApp" +
                        "</a>" +
                        "</div>" +

                        "<p style=\"margin-top: 30px; font-size: 12px; color: #272727;\">Este mensaje proviene del formulario del portafolio SeruhioCode30.</p>" +
                        "</div>";


        body.put("html", html);
        System.out.println("IP = " + emailRequest.getIp());
        System.out.println("URL = " + emailRequest.getUrl());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }


}
