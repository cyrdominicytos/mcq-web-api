package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Teacher;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Teacher data access interface
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public Teacher findOneByUuid(String uuid);
}
