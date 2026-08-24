package com.seruhioCode30.emails.controller;

import com.seruhioCode30.emails.service.MailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CorreoController.class)
class CorreoControllerTests {

    private static final String ALLOWED_ORIGIN = "https://seruhio30.github.io";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MailSenderService mailSenderService;

    @Test
    void validRequestReturnsOk() throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Sergio",
                                  "remitente":"sergio@example.com",
                                  "telefono":"8888-8888",
                                  "categoria":"pagina-web",
                                  "contenido":"Necesito información sobre un proyecto web.",
                                  "url":"https://example.com/contact"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Solicitud enviada correctamente"));

        verify(mailSenderService).enviarCorreo(any());
    }

    @Test
    void allowedProductionOriginReceivesCorsHeader() throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .header("Origin", ALLOWED_ORIGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Sergio",
                                  "remitente":"sergio@example.com",
                                  "telefono":"8888-8888",
                                  "categoria":"pagina-web",
                                  "contenido":"Mensaje válido",
                                  "url":"https://seruhio30.github.io/Portfolio-SeruhioCode30-Front-End/"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", ALLOWED_ORIGIN));

        verify(mailSenderService).enviarCorreo(any());
    }

    @Test
    void arbitraryOriginIsRejectedByCors() throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .header("Origin", "https://evil.example")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Sergio",
                                  "remitente":"sergio@example.com",
                                  "telefono":"8888-8888",
                                  "categoria":"pagina-web",
                                  "contenido":"Mensaje válido",
                                  "url":"https://evil.example/contact"
                                }
                                """))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));

        verify(mailSenderService, never()).enviarCorreo(any());
    }

    @Test
    void allowedProductionOriginPreflightSucceeds() throws Exception {
        mockMvc.perform(options("/correo/enviar")
                        .header("Origin", ALLOWED_ORIGIN)
                        .header("Access-Control-Request-Method", "POST")
                        .header("Access-Control-Request-Headers", "Content-Type"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", ALLOWED_ORIGIN))
                .andExpect(header().string("Access-Control-Allow-Methods", containsString("POST")))
                .andExpect(header().string("Access-Control-Allow-Headers", containsString("Content-Type")));

        verify(mailSenderService, never()).enviarCorreo(any());
    }

    @Test
    void invalidEmailReturnsControlledBadRequest() throws Exception {
        performInvalidRequest("""
                {
                  "nombre":"Sergio",
                  "remitente":"correo-invalido",
                  "telefono":"8888-8888",
                  "categoria":"pagina-web",
                  "contenido":"Mensaje válido",
                  "url":"https://example.com/contact"
                }
                """);
    }

    @Test
    void blankRequiredNameReturnsControlledBadRequest() throws Exception {
        performInvalidRequest("""
                {
                  "nombre":"   ",
                  "remitente":"sergio@example.com",
                  "telefono":"8888-8888",
                  "categoria":"pagina-web",
                  "contenido":"Mensaje válido",
                  "url":"https://example.com/contact"
                }
                """);
    }

    @Test
    void blankContentReturnsControlledBadRequest() throws Exception {
        performInvalidRequest("""
                {
                  "nombre":"Sergio",
                  "remitente":"sergio@example.com",
                  "telefono":"8888-8888",
                  "categoria":"pagina-web",
                  "contenido":"   ",
                  "url":"https://example.com/contact"
                }
                """);
    }

    @Test
    void oversizedContentReturnsControlledBadRequest() throws Exception {
        String content = "a".repeat(5001);

        String payload = """
                {
                  "nombre":"Sergio",
                  "remitente":"sergio@example.com",
                  "telefono":"8888-8888",
                  "categoria":"pagina-web",
                  "contenido":"%s",
                  "url":"https://example.com/contact"
                }
                """.formatted(content);

        performInvalidRequest(payload);
    }

    @Test
    void oversizedNameReturnsControlledBadRequest() throws Exception {
        String name = "a".repeat(101);

        String payload = """
                {
                  "nombre":"%s",
                  "remitente":"sergio@example.com",
                  "telefono":"8888-8888",
                  "categoria":"pagina-web",
                  "contenido":"Mensaje válido",
                  "url":"https://example.com/contact"
                }
                """.formatted(name);

        performInvalidRequest(payload);
    }

    @Test
    void optionalPhoneAcceptsEmptyString() throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Sergio",
                                  "remitente":"sergio@example.com",
                                  "telefono":"",
                                  "categoria":"pagina-web",
                                  "contenido":"Mensaje válido",
                                  "url":"https://example.com/contact"
                                }
                                """))
                .andExpect(status().isOk());

        verify(mailSenderService).enviarCorreo(any());
    }

    @Test
    void optionalPhoneAcceptsInternationalFormat() throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Sergio",
                                  "remitente":"sergio@example.com",
                                  "telefono":"+506 8888-8888 ext. 12",
                                  "categoria":"pagina-web",
                                  "contenido":"Mensaje válido",
                                  "url":"https://example.com/contact"
                                }
                                """))
                .andExpect(status().isOk());

        verify(mailSenderService).enviarCorreo(any());
    }

    @Test
    void unsupportedCategoryReturnsControlledBadRequest() throws Exception {
        performInvalidRequest("""
                {
                  "nombre":"Sergio",
                  "remitente":"sergio@example.com",
                  "telefono":"8888-8888",
                  "categoria":"categoria-inventada",
                  "contenido":"Mensaje válido",
                  "url":"https://example.com/contact"
                }
                """);
    }

    @Test
    void emptyCategoryIsAccepted() throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Sergio",
                                  "remitente":"sergio@example.com",
                                  "telefono":"",
                                  "categoria":"",
                                  "contenido":"Mensaje válido",
                                  "url":"https://example.com/contact"
                                }
                                """))
                .andExpect(status().isOk());

        verify(mailSenderService).enviarCorreo(any());
    }

    @Test
    void malformedJsonReturnsControlledBadRequest() throws Exception {
        performInvalidRequest("""
                {
                  "nombre":"Sergio",
                  "remitente":
                }
                """);
    }

    private void performInvalidRequest(String payload) throws Exception {
        mockMvc.perform(post("/correo/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Invalid contact request."));

        verify(mailSenderService, never()).enviarCorreo(any());
    }
}
