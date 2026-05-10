package com.deploywatch.service;
import com.deploywatch.model.Incident;
import com.deploywatch.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class IncidentService {
    private final IncidentRepository repository;
    public IncidentService(IncidentRepository repository){this.repository=repository;}
    public List<Incident> recent(){return repository.findTop20ByOrderByCreatedAtDesc();}
    public long openCount(){return repository.countByResolvedFalse();}
    public Incident resolve(Long id){
        Incident i=repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Incident "+id+" not found"));
        i.setResolved(true);return repository.save(i);
    }
}
