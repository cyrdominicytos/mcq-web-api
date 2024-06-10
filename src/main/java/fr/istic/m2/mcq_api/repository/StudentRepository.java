package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Student data access interface
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findByUuid(String uuid);
}
