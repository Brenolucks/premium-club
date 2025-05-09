package brenolucks.premiumclub.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.stripe.model.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StripeWebhookServiceImpl implements StripeWebhookService{
    @Value("${spring.sendgrid.api-key}")
    private String sendGridAPIKey;

    @Override
    public String handleInvoicePaid(Invoice invoice) throws IOException {
        // Extract important details
        String customerId = invoice.getCustomer();
        String subscriptionId = invoice.getId();
        Long amountPaid = invoice.getAmountPaid(); // in cents
        boolean isFirstPayment = "subscription_create".equals(invoice.getBillingReason());

        // Business logic:
        if (isFirstPayment) {
            // First payment = new subscription
            System.out.println("New subscription created for customer: " + customerId);
            // Update your database (activate user access)
            if (sendGridAPIKey == null || sendGridAPIKey.isEmpty()) {
                throw new RuntimeException("SendGrid API key is not configured");
            }
            Email from = new Email("brenolucks@gmail.com");
            String subject = "Sending with Twilio SendGrid is Fun";
            Email to = new Email("janna.thresh@gmail.com");
            Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendGridAPIKey);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                System.out.println("Using API key: " + sendGridAPIKey.substring(0, 5) + "...");
                System.out.println("Request body: " + mail.build());
                Response response = sg.api(request);
                System.out.println("Response status: " + response.getStatusCode());
                System.out.println("Response body: " + response.getBody());

                if (response.getStatusCode() >= 400) {
                    throw new RuntimeException("SendGrid API error: " + response.getBody());
                }
            } catch (IOException ex) {
                System.err.println("SendGrid API call failed: " + ex.getMessage());
                throw ex;
            }
        } else {
            // Recurring payment
            System.out.println("Recurring payment received for subscription: " + subscriptionId);
        }

        // TODO: Update your database here
        // Example: userService.activatePremiumAccess(customerId);

        return "Invoice paid handled successfully";
    }
}
