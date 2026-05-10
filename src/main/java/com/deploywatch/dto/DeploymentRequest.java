package com.deploywatch.dto;
import com.deploywatch.model.DeploymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class DeploymentRequest {
    @NotBlank private String applicationName;
    @NotBlank private String environment;
    @NotBlank private String version;
    private String commitId;
    private String triggeredBy;
    @NotNull private DeploymentStatus status;
    private String message;
    public String getApplicationName(){return applicationName;} public void setApplicationName(String v){applicationName=v;}
    public String getEnvironment(){return environment;} public void setEnvironment(String v){environment=v;}
    public String getVersion(){return version;} public void setVersion(String v){version=v;}
    public String getCommitId(){return commitId;} public void setCommitId(String v){commitId=v;}
    public String getTriggeredBy(){return triggeredBy;} public void setTriggeredBy(String v){triggeredBy=v;}
    public DeploymentStatus getStatus(){return status;} public void setStatus(DeploymentStatus v){status=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
}
