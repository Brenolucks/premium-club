package brenolucks.premiumclub.service.stripe;

import brenolucks.premiumclub.exceptions.InvalidTypeException;
import brenolucks.premiumclub.model.enums.PlanType;
import brenolucks.premiumclub.repository.user.UsersRepository;
import brenolucks.premiumclub.service.user.UserService;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StripeServiceImpl implements StripeService {
    @Autowired
    StripeClient stripeClient;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserService userService;

    @Override
    public String createSubscription(String email, String priceID, PlanType planType) {
        boolean existsUser = usersRepository.existsByEmail(email);

        if(existsUser) {
            userService.existUserWithThisPlan(false, email, planType);
        }

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setCustomerEmail(email)
                            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                            .setSuccessUrl("http://localhost:8080/api/checkout-ok")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPrice(priceID)
                                            .setQuantity(1L)
                                            .build()
                            )
                            .build();
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException | RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getPriceByPlanType(PlanType type) {
        PlanType planType = null;

        try {
            planType = PlanType.valueOf(type.toString());

            return switch (planType) {
                case DAILY -> "price_1RMZ6AERVgp1fq6wYCp6mCHP";
                case MONTHLY -> "price_1RMZ6AERVgp1fq6whQYkZlCG";
                case YEARLY -> "price_1RMZ6AERVgp1fq6wDo7s61O3";
            };
        } catch (IllegalArgumentException ex) {
            throw new InvalidTypeException(
                    "Invalid plan type: '" + type + "'. Valid options are: " +
                            Arrays.toString(PlanType.values()));
        }
    }
}
