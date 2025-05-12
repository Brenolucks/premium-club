package brenolucks.premiumclub.service.stripe;

import brenolucks.premiumclub.model.enums.PlanType;
import brenolucks.premiumclub.service.user.UserService;
import brenolucks.premiumclub.service.email.EmailService;
import com.stripe.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StripeWebhookServiceImpl implements StripeWebhookService {
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Override
    public String handleInvoicePaid(Invoice invoice) throws IOException {
        String subscriptionId = invoice.getId();
        boolean isFirstPayment = "subscription_create".equals(invoice.getBillingReason());
        Long amountPaid = invoice.getAmountPaid();
        PlanType plan = null;

        if (amountPaid == 100L) {
            plan = PlanType.DAILY;
        } else if (amountPaid == 1000L) {
            plan = PlanType.MONTHLY;
        } else if (amountPaid == 10000L) {
            plan = PlanType.YEARLY;
        }

        if (isFirstPayment) {
            userService.saveNewUserAndSubscription(invoice.getCustomerName(), invoice.getCustomerEmail(), plan, invoice.getId());
            emailService.sendEmail(invoice.getCustomerEmail());
        } else {
            System.out.println("Recurring payment received for subscription: " + subscriptionId);
        }

        return "Invoice paid handled successfully";
    }
}
