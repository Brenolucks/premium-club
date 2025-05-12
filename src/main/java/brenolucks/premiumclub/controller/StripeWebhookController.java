package brenolucks.premiumclub.controller;

import brenolucks.premiumclub.service.stripe.StripeWebhookServiceImpl;
import brenolucks.premiumclub.service.user.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class StripeWebhookController {
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
    @Autowired
    StripeWebhookServiceImpl stripeWebhookService;
    @Autowired
    UserService userService;

    @PostMapping("/api/webhook")
    public String handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) throws IOException {

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            return "Invalid signature";
        }

        if ("invoice.paid".equals(event.getType())) {
            Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().get();
            return stripeWebhookService.handleInvoicePaid(invoice);
        }

        return "Unhandled event type";
    }
}
