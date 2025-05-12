package brenolucks.premiumclub.service.stripe;

import brenolucks.premiumclub.model.enums.PlanType;
import com.stripe.exception.StripeException;

public interface StripeService {
    String createSubscription(String email, String priceID, PlanType planType) throws StripeException;
    String getPriceByPlanType(PlanType planType) throws StripeException;
}
