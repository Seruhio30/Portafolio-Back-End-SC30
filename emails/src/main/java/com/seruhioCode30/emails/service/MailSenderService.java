package com.seruhioCode30.emails.service;

import com.seruhioCode30.emails.dto.EmailRequest;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailSenderService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreo(EmailRequest emailRequest) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

            helper.setTo("seruhiocode30@gmail.com"); // ✅ Siempre enviará el correo a tu dirección
            helper.setFrom(emailRequest.getRemitente()); // ✅ Correo del remitente
            helper.setSubject("Nuevo contacto: " + emailRequest.getNombre());

            // ✅ Cuerpo del correo con toda la información
            String contenido = "<strong>Nombre:</strong> " + emailRequest.getNombre() + "<br>" +
                    "<strong>Correo:</strong> " + emailRequest.getRemitente() + "<br>" +
                    "<strong>Teléfono:</strong> " + emailRequest.getTelefono() + "<br>" +
                    "<strong>Área de interés:</strong> " + emailRequest.getCategoria() + "<br><br>" +
                    "<strong>Mensaje:</strong><br>" + emailRequest.getContenido();

            helper.setText(contenido, true);

            mailSender.send(mensaje);
            System.out.println("✅ Correo recibido de: " + emailRequest.getRemitente());
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}