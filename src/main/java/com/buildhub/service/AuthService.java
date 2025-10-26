package com.buildhub.service;

import com.buildhub.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final String SECRET = "your-secret-key-change-this-in-production";
    private final long EXPIRATION_TIME = 86400000; // 24 hours

    public User registerUser(User user) {
        // Check if user already exists
        String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, user.getEmail());
        
        if (count > 0) {
            throw new RuntimeException("User already exists with this email");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        // Insert user
        String insertQuery = "INSERT INTO users (name, email, password, role, phone, address, license_number) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(insertQuery,
            user.getName(),
            user.getEmail(),
            hashedPassword,
            user.getRole(),
            user.getPhone(),
            user.getAddress(),
            user.getLicenseNumber() != null ? user.getLicenseNumber() : ""
        );

        // Return created user (without password)
        return findUserByEmail(user.getEmail());
    }

    public String login(String email, String password) {
        User user = findUserByEmail(email);
        
        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        return generateToken(user);
    }

    public User findUserByEmail(String email) {
        try {
            String query = "SELECT * FROM users WHERE email = ?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setLicenseNumber(rs.getString("license_number"));
                user.setExperience(rs.getString("experience"));
                user.setSpecialization(rs.getString("specialization"));
                user.setDomainOfExpertise(rs.getString("domain_of_expertise"));
                user.setSkills(rs.getString("skills"));
                user.setHourlyRate(rs.getDouble("hourly_rate"));
                user.setMinBudget(rs.getDouble("min_budget"));
                user.setMaxBudget(rs.getDouble("max_budget"));
                user.setAvailability(rs.getString("availability"));
                user.setPortfolioUrl(rs.getString("portfolio_url"));
                user.setCustomerApprovalRatio(rs.getDouble("customer_approval_ratio"));
                user.setProjectsCompleted(rs.getInt("projects_completed"));
                user.setAverageRating(rs.getDouble("average_rating"));
                user.setAcceptancePerConsultationRatio(rs.getDouble("acceptance_per_consultation_ratio"));
                user.setIsVerified(rs.getInt("is_verified") == 1);
                user.setAvatar(rs.getString("avatar"));
                user.setCreatedAt(rs.getString("created_at"));
                return user;
            }, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findUserById(Integer id) {
        String query = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(null); // Don't return password
            user.setRole(rs.getString("role"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setLicenseNumber(rs.getString("license_number"));
            user.setExperience(rs.getString("experience"));
            user.setSpecialization(rs.getString("specialization"));
            user.setDomainOfExpertise(rs.getString("domain_of_expertise"));
            user.setSkills(rs.getString("skills"));
            user.setHourlyRate(rs.getDouble("hourly_rate"));
            user.setMinBudget(rs.getDouble("min_budget"));
            user.setMaxBudget(rs.getDouble("max_budget"));
            user.setAvailability(rs.getString("availability"));
            user.setPortfolioUrl(rs.getString("portfolio_url"));
            user.setCustomerApprovalRatio(rs.getDouble("customer_approval_ratio"));
            user.setProjectsCompleted(rs.getInt("projects_completed"));
            user.setAverageRating(rs.getDouble("average_rating"));
            user.setAcceptancePerConsultationRatio(rs.getDouble("acceptance_per_consultation_ratio"));
            user.setIsVerified(rs.getInt("is_verified") == 1);
            user.setAvatar(rs.getString("avatar"));
            user.setCreatedAt(rs.getString("created_at"));
            return user;
        }, id);
    }

    private String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
}

