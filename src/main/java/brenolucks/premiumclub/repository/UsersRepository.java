package brenolucks.premiumclub.repository;

import brenolucks.premiumclub.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
