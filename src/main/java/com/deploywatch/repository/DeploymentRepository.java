package com.deploywatch.repository;
import com.deploywatch.model.Deployment;
import com.deploywatch.model.DeploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DeploymentRepository extends JpaRepository<Deployment,Long> {
    List<Deployment> findTop20ByOrderByCreatedAtDesc();
    long countByStatus(DeploymentStatus status);
}
