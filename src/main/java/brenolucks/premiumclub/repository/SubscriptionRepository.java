package brenolucks.premiumclub.repository;

import brenolucks.premiumclub.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
