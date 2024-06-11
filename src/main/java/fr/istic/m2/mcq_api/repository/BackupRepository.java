package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Backup;
import fr.istic.m2.mcq_api.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Backup data access interface
 */
@Repository
public interface BackupRepository extends JpaRepository<Backup, Long> {
    // You can define additional methods here if needed
   // @Query("SELECT b FROM Backup b WHERE b.teacher.id = :teacherId")
    Backup findBackupByTeacherId(Long teacherId);
}
