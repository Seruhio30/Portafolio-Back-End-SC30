package com.seruhioCode30.emails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("seruhiocode30@gmail.com");
        mailSender.setPassword("rhfgaiifknoibgxo"); // Usa la contraseña de aplicación

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Añadir confianza SSL
        props.put("mail.smtp.connectiontimeout", "5000"); // Ajustar timeout
        props.put("mail.smtp.timeout", "5000"); // Ajustar timeout
        props.put("mail.smtp.writetimeout", "5000"); // Ajustar timeout

        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}