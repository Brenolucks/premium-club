package brenolucks.premiumclub.service.subscription;

import brenolucks.premiumclub.model.enums.PlanType;
import brenolucks.premiumclub.model.Subscription;
import brenolucks.premiumclub.model.Users;
import brenolucks.premiumclub.repository.subscription.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Override
    public void saveNewSubscription(PlanType planType, String stripeSubsID, Users usersID) {
        Subscription subscription = new Subscription();
        subscription.setStripeSubscriptionID(stripeSubsID);
        subscription.setPlanType(PlanType.valueOf(planType.toString()));
        subscription.setUsersID(usersID);

        subscriptionRepository.save(subscription);
    }
}
