package com.sebamutuku.sebastianmutukuspringtest.repos;

import com.sebamutuku.sebastianmutukuspringtest.entities.Project;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepo extends JpaRepository<Project, String> {
    @Query("select p from Project  p where p.name=:name")
    Optional<Project> findByName(String name);

    @Query("select p from Project  p where p.projectId=:projectId")
    Optional<Project> findByProjectId(String projectId);

}
