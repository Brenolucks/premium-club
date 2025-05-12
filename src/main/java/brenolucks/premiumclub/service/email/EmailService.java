package brenolucks.premiumclub.service.email;

import java.io.IOException;

public interface EmailService {
    void sendEmail(String emailUser) throws IOException;
}
