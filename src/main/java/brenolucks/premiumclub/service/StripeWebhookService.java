package brenolucks.premiumclub.service;

import com.stripe.model.Event;
import com.stripe.model.Invoice;

public interface StripeWebhookService {
    public String handleInvoicePaid(Invoice invoice);
}
