package com.deploywatch.controller;
import com.deploywatch.model.DeploymentStatus;
import com.deploywatch.service.DeploymentService;
import com.deploywatch.service.IncidentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DashboardController {
    private final DeploymentService deployments; private final IncidentService incidents;
    public DashboardController(DeploymentService deployments,IncidentService incidents){this.deployments=deployments;this.incidents=incidents;}
    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("deployments",deployments.recent());
        model.addAttribute("incidents",incidents.recent());
        model.addAttribute("successCount",deployments.count(DeploymentStatus.SUCCESS));
        model.addAttribute("failedCount",deployments.count(DeploymentStatus.FAILED));
        model.addAttribute("runningCount",deployments.count(DeploymentStatus.RUNNING));
        model.addAttribute("openIncidentCount",incidents.openCount());
        return "dashboard";
    }
}
