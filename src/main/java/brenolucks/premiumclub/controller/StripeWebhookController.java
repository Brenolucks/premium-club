package brenolucks.premiumclub.controller;

import brenolucks.premiumclub.service.StripeWebhookServiceImpl;
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

@RestController
public class StripeWebhookController {
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
    @Autowired
    StripeWebhookServiceImpl stripeWebhookService;

    @PostMapping("/api/webhook")
    public String handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            // Verify the webhook signature
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return "Invalid signature";
        }

        // Handle invoice.paid event
        if ("invoice.paid".equals(event.getType())) {
            Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().get();
            return stripeWebhookService.handleInvoicePaid(invoice);
        }

        return "Unhandled event type";
    }
}
