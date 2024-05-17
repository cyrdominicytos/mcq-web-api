package fr.istic.science.repository;

import fr.istic.science.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can define additional methods here if needed
}
