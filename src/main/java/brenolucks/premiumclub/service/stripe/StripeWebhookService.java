package brenolucks.premiumclub.service.stripe;

import com.stripe.model.Invoice;

import java.io.IOException;

public interface StripeWebhookService {
    public String handleInvoicePaid(Invoice invoice) throws IOException;
}
