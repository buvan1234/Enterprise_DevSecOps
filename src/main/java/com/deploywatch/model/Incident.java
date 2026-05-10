package com.deploywatch.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "incidents")
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "deployment_id") private Deployment deployment;
    @Column(nullable = false) private String severity;
    @Column(nullable = false, length = 1000) private String description;
    @Column(nullable = false) private LocalDateTime createdAt;
    private boolean resolved;
    @PrePersist void onCreate(){createdAt=LocalDateTime.now();}
    public Long getId(){return id;}
    public Deployment getDeployment(){return deployment;} public void setDeployment(Deployment v){deployment=v;}
    public String getSeverity(){return severity;} public void setSeverity(String v){severity=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public boolean isResolved(){return resolved;} public void setResolved(boolean v){resolved=v;}
}
