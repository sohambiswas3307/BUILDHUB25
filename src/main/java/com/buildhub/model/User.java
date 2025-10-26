package com.buildhub.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role; // customer, contractor, labour
    private String phone;
    private String address;
    private String licenseNumber;
    private String experience;
    private String specialization;
    private String domainOfExpertise;
    private String skills;
    private Double hourlyRate;
    private Double minBudget;
    private Double maxBudget;
    private String availability;
    private String portfolioUrl;
    private Double customerApprovalRatio;
    private Integer projectsCompleted;
    private Double averageRating;
    private Double acceptancePerConsultationRatio;
    private Boolean isVerified;
    private String avatar;
    private String createdAt;
}

