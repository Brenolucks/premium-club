package brenolucks.premiumclub.service;

import com.stripe.model.Event;
import com.stripe.model.Invoice;

import java.io.IOException;

public interface StripeWebhookService {
    public String handleInvoicePaid(Invoice invoice) throws IOException;
}
