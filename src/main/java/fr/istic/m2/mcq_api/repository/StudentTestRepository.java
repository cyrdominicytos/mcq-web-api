package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * StudentTest data access interface
 */
@Repository
public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {
    // You can define additional methods here if needed
    @Query("SELECT s FROM StudentTest s WHERE s.student.id = :studentId")
    List<StudentTest> findStudentTestByStudentId(@Param("studentId") Long studentId);
}