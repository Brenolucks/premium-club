package brenolucks.premiumclub.service;

import com.stripe.exception.StripeException;

public interface StripeService {
    public String createSubscription(String email, String planType) throws StripeException;
    public String getPriceByPlanType(String planType) throws StripeException;
}
