package brenolucks.premiumclub.service;

import com.stripe.model.Invoice;
import org.springframework.stereotype.Service;

@Service
public class StripeWebhookServiceImpl implements StripeWebhookService{

    @Override
    public String handleInvoicePaid(Invoice invoice) {
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
        } else {
            // Recurring payment
            System.out.println("Recurring payment received for subscription: " + subscriptionId);
        }

        // TODO: Update your database here
        // Example: userService.activatePremiumAccess(customerId);

        return "Invoice paid handled successfully";
    }
}
