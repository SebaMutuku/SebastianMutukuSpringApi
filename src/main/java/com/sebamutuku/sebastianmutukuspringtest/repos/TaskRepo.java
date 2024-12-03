package com.sebamutuku.sebastianmutukuspringtest.repos;

import com.sebamutuku.sebastianmutukuspringtest.entities.Task;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND (:status IS NULL OR t.status = :status) AND (:dueDate IS NULL OR t.dueDate = :dueDate)")
    Page<Task> findByProjectIdAndFilters(@Param("projectId") UUID projectId, @Param("status") String status, @Param("dueDate") Date dueDate, Pageable pageable);

    List<Task> findByProjectId(UUID projectId);

}
