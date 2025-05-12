package brenolucks.premiumclub.service.subscription;

import brenolucks.premiumclub.model.enums.PlanType;
import brenolucks.premiumclub.model.Users;

public interface SubscriptionService {
    void saveNewSubscription(PlanType planType, String stripeSubsID, Users userID);
}
