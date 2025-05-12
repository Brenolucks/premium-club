package brenolucks.premiumclub.service.user;

import brenolucks.premiumclub.model.enums.PlanType;

public interface UserService {
    void existUserWithThisPlan(boolean existUser, String email, PlanType plan);
    void saveNewUserAndSubscription(String name, String email, PlanType planType, String stripeSubsID);
}
