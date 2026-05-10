package com.deploywatch.controller;
import com.deploywatch.dto.DeploymentRequest;
import com.deploywatch.model.*;
import com.deploywatch.service.DeploymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/deployments")
public class DeploymentApiController {
    private final DeploymentService service;
    public DeploymentApiController(DeploymentService service){this.service=service;}
    @GetMapping public List<Deployment> all(){return service.recent();}
    @GetMapping("/{id}") public Deployment get(@PathVariable Long id){return service.find(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENGINEER')")
    public Deployment create(@Valid @RequestBody DeploymentRequest request){return service.create(request);}
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENGINEER')")
    public Deployment updateStatus(@PathVariable Long id,@RequestBody Map<String,String> body){
        DeploymentStatus status;
        try {status=DeploymentStatus.valueOf(body.getOrDefault("status","").toUpperCase());}
        catch(IllegalArgumentException ex){throw new IllegalArgumentException("status must be QUEUED, RUNNING, SUCCESS, or FAILED");}
        return service.updateStatus(id,status,body.get("message"));
    }
}
