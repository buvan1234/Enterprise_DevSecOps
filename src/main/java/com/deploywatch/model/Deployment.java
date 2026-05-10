package com.deploywatch.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "deployments")
public class Deployment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String applicationName;
    @Column(nullable = false) private String environment;
    @Column(nullable = false) private String version;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private DeploymentStatus status;
    private String commitId;
    private String triggeredBy;
    @Column(length = 1000) private String message;
    @Column(nullable = false) private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate void onUpdate() { updatedAt = LocalDateTime.now(); }
    public Long getId(){return id;}
    public String getApplicationName(){return applicationName;} public void setApplicationName(String v){applicationName=v;}
    public String getEnvironment(){return environment;} public void setEnvironment(String v){environment=v;}
    public String getVersion(){return version;} public void setVersion(String v){version=v;}
    public DeploymentStatus getStatus(){return status;} public void setStatus(DeploymentStatus v){status=v;}
    public String getCommitId(){return commitId;} public void setCommitId(String v){commitId=v;}
    public String getTriggeredBy(){return triggeredBy;} public void setTriggeredBy(String v){triggeredBy=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}
