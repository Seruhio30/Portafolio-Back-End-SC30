package com.seruhioCode30.emails;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "MAIL_USER=test@example.invalid",
        "MAIL_PASS=test-mail-password",
        "RESEND_API_KEY=test-resend-api-key"
})
class EmailsApplicationTests {

    @Test
    void contextLoads() {
    }

}
