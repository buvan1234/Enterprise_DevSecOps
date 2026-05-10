package com.deploywatch.service;
import com.deploywatch.dto.DeploymentRequest;
import com.deploywatch.model.*;
import com.deploywatch.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class DeploymentService {
    private final DeploymentRepository deployments;
    private final IncidentRepository incidents;
    public DeploymentService(DeploymentRepository deployments, IncidentRepository incidents){this.deployments=deployments;this.incidents=incidents;}
    public List<Deployment> recent(){return deployments.findTop20ByOrderByCreatedAtDesc();}
    public long count(DeploymentStatus status){return deployments.countByStatus(status);}
    public Deployment find(Long id){return deployments.findById(id).orElseThrow(()->new ResourceNotFoundException("Deployment "+id+" not found"));}
    @Transactional
    public Deployment create(DeploymentRequest r){
        Deployment d=new Deployment(); d.setApplicationName(r.getApplicationName()); d.setEnvironment(r.getEnvironment());
        d.setVersion(r.getVersion()); d.setCommitId(r.getCommitId()); d.setTriggeredBy(r.getTriggeredBy());
        d.setStatus(r.getStatus()); d.setMessage(r.getMessage()); d=deployments.save(d);
        if(d.getStatus()==DeploymentStatus.FAILED) createIncident(d, r.getMessage());
        return d;
    }
    @Transactional
    public Deployment updateStatus(Long id, DeploymentStatus status, String message){
        Deployment d=find(id); d.setStatus(status); if(message!=null)d.setMessage(message);
        if(status==DeploymentStatus.FAILED)createIncident(d,message);
        return deployments.save(d);
    }
    private void createIncident(Deployment d,String message){
        Incident i=new Incident(); i.setDeployment(d); i.setSeverity("HIGH");
        i.setDescription(message==null||message.isBlank()?"Deployment failed. Check the pipeline logs.":message);
        incidents.save(i);
    }
}
