package com.seruhioCode30.emails.controller;

import com.seruhioCode30.emails.dto.EmailRequest;
import com.seruhioCode30.emails.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/correo")
public class CorreoController {

    private final MailSenderService mailSenderService;

    @Autowired
    public CorreoController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarCorreo(@RequestBody EmailRequest emailRequest) {
        System.out.println("🧐 Correo recibido de: " + emailRequest.getRemitente()); // ✅ Verifica el remitente
        mailSenderService.enviarCorreo(emailRequest);
        return ResponseEntity.ok("Solicitud enviada correctamente");
    }

}
