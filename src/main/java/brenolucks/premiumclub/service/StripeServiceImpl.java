package brenolucks.premiumclub.service;

import com.stripe.Stripe;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class StripeServiceImpl implements StripeService {
    @Autowired
    StripeClient stripeClient;

    @Override
    public String createSubscription(String email, String priceID) {
        //create a subscription and redirect to check out session

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setCustomerEmail(email)
                            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                            .setSuccessUrl("http://localhost:8080/checkout-session")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPrice(priceID)
                                            .setQuantity(1L)
                                            .build()
                            )
                            .build();
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getPriceByPlanType(String planType) {
        if (planType.equals("DAILY")) {
            planType = "price_1RMZ6AERVgp1fq6wYCp6mCHP";
        } else if (planType.equals("MONTHLY")) {
            planType = "price_1RMZ6AERVgp1fq6whQYkZlCG";
        } else {
            planType = "price_1RMZ6AERVgp1fq6wDo7s61O3";
        }

        return planType;
    }
}
