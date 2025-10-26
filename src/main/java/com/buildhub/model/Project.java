package com.buildhub.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Integer id;
    private String title;
    private String description;
    private String location;
    private Double budget;
    private String startDate;
    private String endDate;
    private String status; // planning, in-progress, completed, cancelled
    private Integer customerId;
    private Integer contractorId;
    private String completedDetails;
    private String completionDate;
    private Boolean customerApproved;
    private String createdAt;
}

