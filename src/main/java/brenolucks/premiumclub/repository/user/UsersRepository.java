package brenolucks.premiumclub.repository.user;

import brenolucks.premiumclub.model.enums.PlanType;
import brenolucks.premiumclub.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndSubscription_PlanType(@Param("email") String email, @Param("plan_type") PlanType planType);
}
