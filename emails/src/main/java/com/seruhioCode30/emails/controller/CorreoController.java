package com.seruhioCode30.emails.controller;

import com.seruhioCode30.emails.dto.EmailRequest;
import com.seruhioCode30.emails.service.MailSenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(
        origins = "https://seruhio30.github.io",
        methods = RequestMethod.POST,
        allowedHeaders = "Content-Type"
)
@RestController
@RequestMapping("/correo")
public class CorreoController {

    private final MailSenderService mailSenderService;

    @Autowired
    public CorreoController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarCorreo(@Valid @RequestBody EmailRequest emailRequest) {
        mailSenderService.enviarCorreo(emailRequest);
        return ResponseEntity.ok("Solicitud enviada correctamente");
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Map<String, String>> handleInvalidRequest(Exception exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "status", "BAD_REQUEST",
                "message", "Invalid contact request."
        ));
    }
}
