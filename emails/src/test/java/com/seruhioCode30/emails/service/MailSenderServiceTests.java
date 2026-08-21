package com.seruhioCode30.emails.service;

import com.seruhioCode30.emails.dto.EmailRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MailSenderServiceTests {

    private final MailSenderService mailSenderService = new MailSenderService();

    @Test
    void buildHtmlEscapesUserControlledContentAcrossAllFields() {
        EmailRequest request = new EmailRequest();
        request.setNombre("<b>Sergio</b>");
        request.setRemitente("test\"'@example.com");
        request.setTelefono("123 & 456");
        request.setCategoria("<script>alert('x')</script>");
        request.setContenido("Prueba <b>texto</b> & \"comillas\" 'simples'");
        request.setUrl("https://example.com/?q=<tag>&value=\"test\"");

        String html = mailSenderService.buildHtml(request, "2026-08-20 18:30:00");

        assertTrue(html.contains("&lt;b&gt;Sergio&lt;/b&gt;"));
        assertFalse(html.contains("<b>Sergio</b>"));

        assertTrue(html.contains("&lt;script&gt;alert(&#39;x&#39;)&lt;/script&gt;"));
        assertFalse(html.contains("<script>alert('x')</script>"));

        assertTrue(html.contains("123 &amp; 456"));

        assertTrue(html.contains("test&quot;&#39;@example.com"));
        assertTrue(html.contains("href=\"mailto:test&quot;&#39;@example.com\""));
        assertFalse(html.contains("test\"'@example.com"));

        assertTrue(html.contains("Prueba &lt;b&gt;texto&lt;/b&gt; &amp; &quot;comillas&quot; &#39;simples&#39;"));
        assertFalse(html.contains("Prueba <b>texto</b> & \"comillas\" 'simples'"));

        assertTrue(html.contains("https://example.com/?q=&lt;tag&gt;&amp;value=&quot;test&quot;"));
        assertTrue(html.contains("href=\"https://example.com/?q=&lt;tag&gt;&amp;value=&quot;test&quot;\""));
        assertFalse(html.contains("https://example.com/?q=<tag>&value=\"test\""));

        assertTrue(html.contains("<p><strong>Nombre:</strong> "));
        assertTrue(html.contains("<p><strong>Email:</strong> "));
        assertTrue(html.contains("<p><strong>Teléfono:</strong> "));
        assertTrue(html.contains("<p><strong>Categoría:</strong> "));
        assertTrue(html.contains("<p><strong>URL:</strong> <a href=\""));
        assertTrue(html.contains("<p style=\"white-space: pre-wrap;\">"));
        assertTrue(html.contains("<a href=\"mailto:"));
    }

    @Test
    void buildHtmlPreservesNormalText() {
        EmailRequest request = new EmailRequest();
        request.setNombre("Sergio Herrera");
        request.setRemitente("sergio@example.com");
        request.setTelefono("8888-8888");
        request.setCategoria("Proyecto web");
        request.setContenido("Necesito una pagina para mi negocio.");
        request.setUrl("https://example.com/contact");

        String html = mailSenderService.buildHtml(request, "2026-08-20 18:30:00");

        assertTrue(html.contains("Sergio Herrera"));
        assertTrue(html.contains("sergio@example.com"));
        assertTrue(html.contains("8888-8888"));
        assertTrue(html.contains("Proyecto web"));
        assertTrue(html.contains("Necesito una pagina para mi negocio."));
        assertTrue(html.contains("https://example.com/contact"));
    }
}
