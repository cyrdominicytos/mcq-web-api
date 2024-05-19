package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Teacher;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Teacher data access interface
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // You can define additional methods here if needed
}
