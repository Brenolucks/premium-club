package brenolucks.premiumclub.service.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.sendgrid.api-key}")
    private String sendGridAPIKey;

    @Value("${email.test}")
    private String email;

    @Override
    public void sendEmail(String emailUser) throws IOException {
        Mail emailDetails = getMail(emailUser);

        SendGrid sg = new SendGrid(sendGridAPIKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(emailDetails.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid API error: " + response.getBody());
            }
        } catch (IOException ex) {
            System.err.println("SendGrid API call failed: " + ex.getMessage());
            throw ex;
        }
    }

    private Mail getMail(String emailUser) {
        if (sendGridAPIKey == null || sendGridAPIKey.isEmpty()) {
            throw new RuntimeException("SendGrid API key is not configured");
        } else if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email is not configured");
        }

        Email from = new Email(email);
        String subject = "PREMIUM CLUB";
        Email to = new Email(emailUser);
        Content content = new Content("text/plain", "Welcome...!");
        return new Mail(from, subject, to, content);
    }
}
