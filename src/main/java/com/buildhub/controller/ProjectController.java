package com.buildhub.controller;

import com.buildhub.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
public class ProjectController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        String query = "SELECT * FROM projects ORDER BY created_at DESC";
        List<Project> projects = jdbcTemplate.query(query, (rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getInt("id"));
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setLocation(rs.getString("location"));
            project.setBudget(rs.getDouble("budget"));
            project.setStartDate(rs.getString("start_date"));
            project.setEndDate(rs.getString("end_date"));
            project.setStatus(rs.getString("status"));
            project.setCustomerId(rs.getInt("customer_id"));
            project.setContractorId(rs.getObject("contractor_id") != null ? rs.getInt("contractor_id") : null);
            project.setCompletedDetails(rs.getString("completed_details"));
            project.setCompletionDate(rs.getString("completion_date"));
            project.setCustomerApproved(rs.getInt("customer_approved") == 1);
            project.setCreatedAt(rs.getString("created_at"));
            return project;
        });
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        String query = "INSERT INTO projects (title, description, location, budget, start_date, end_date, status, customer_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(query,
            project.getTitle(),
            project.getDescription(),
            project.getLocation(),
            project.getBudget(),
            project.getStartDate(),
            project.getEndDate(),
            project.getStatus(),
            project.getCustomerId()
        );
        
        return ResponseEntity.ok(Map.of("message", "Project created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        String query = "SELECT * FROM projects WHERE id = ?";
        Project project = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            Project p = new Project();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setDescription(rs.getString("description"));
            p.setLocation(rs.getString("location"));
            p.setBudget(rs.getDouble("budget"));
            p.setStartDate(rs.getString("start_date"));
            p.setEndDate(rs.getString("end_date"));
            p.setStatus(rs.getString("status"));
            p.setCustomerId(rs.getInt("customer_id"));
            p.setContractorId(rs.getObject("contractor_id") != null ? rs.getInt("contractor_id") : null);
            p.setCompletedDetails(rs.getString("completed_details"));
            p.setCompletionDate(rs.getString("completion_date"));
            p.setCustomerApproved(rs.getInt("customer_approved") == 1);
            p.setCreatedAt(rs.getString("created_at"));
            return p;
        }, id);
        return ResponseEntity.ok(project);
    }
}

