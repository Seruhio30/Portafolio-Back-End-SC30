package com.seruhioCode30.emails;

import com.seruhioCode30.emails.service.MailSenderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootApplication
public class EmailsApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EmailsApplication.class, args);

		// Obtener la instancia del servicio desde el contexto de Spring
		MailSenderService mailService = context.getBean(MailSenderService.class);

		// Probar el envío de correo
		//mailService.enviarCorreo("suruhiocode30@gmail.com", "Prueba desde Spring Boot", "Este es un mensaje de prueba.");
	}
}