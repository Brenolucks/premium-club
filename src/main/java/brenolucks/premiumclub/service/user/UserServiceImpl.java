package brenolucks.premiumclub.service.user;

import brenolucks.premiumclub.exceptions.UserExistWithPlanException;
import brenolucks.premiumclub.model.enums.PlanType;
import brenolucks.premiumclub.model.enums.Status;
import brenolucks.premiumclub.model.Users;
import brenolucks.premiumclub.repository.user.UsersRepository;
import brenolucks.premiumclub.service.subscription.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void existUserWithThisPlan(boolean existUser, String email, PlanType plan) {
        if (existUser) {
            throw new UserExistWithPlanException("Exist a user with this email: " + email + ", active!");
        }
    }

    @Override
    public void saveNewUserAndSubscription(String name, String email, PlanType planType, String stripeSubsID) {
        Users users = new Users();
        users.setName(name);
        users.setEmail(email);
        users.setStatus(Status.ACTIVE);
        usersRepository.save(users);
        subscriptionService.saveNewSubscription(planType, stripeSubsID, users);
    }
}
