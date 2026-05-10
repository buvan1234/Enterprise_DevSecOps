package com.deploywatch.controller;
import com.deploywatch.model.Incident;
import com.deploywatch.service.IncidentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/incidents")
public class IncidentApiController {
    private final IncidentService service;
    public IncidentApiController(IncidentService service){this.service=service;}
    @GetMapping public List<Incident> all(){return service.recent();}
    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ENGINEER')")
    public Incident resolve(@PathVariable Long id){return service.resolve(id);}
}
