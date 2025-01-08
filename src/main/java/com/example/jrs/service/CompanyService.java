package com.example.jrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jrs.dto.RegisterDto;
import com.example.jrs.entity.Company;
import com.example.jrs.entity.UserAuthSchema;
import com.example.jrs.enums.UserRole;
import com.example.jrs.repo.CompanyRepository;

import jakarta.validation.ValidationException;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public Company registerCompany(RegisterDto registerDto) {
        // Validate basic required fields
        if (registerDto.getUsername() == null || registerDto.getPassword() == null) {
            throw new ValidationException("Username and password are required");
        }

        // Create UserAuthSchema with basic auth info
        UserAuthSchema userAuth = new UserAuthSchema();
        userAuth.setUsername(registerDto.getUsername());
        userAuth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userAuth.setRole(UserRole.EMPLOYER);
        userAuth.setActive(true);

        // Create Compay with minimal required information
        Company company = new Company();
        if (registerDto.getProfileData() != null) {
            company.setCompanyName(registerDto.getProfileData().getName());
            company.setEmail(registerDto.getProfileData().getEmail());
        }
        company.setUserAuthSchema(userAuth);

        // Save the basic profile
        return companyRepository.save(company);
    }

    public Company toggleDocumentVerification(Long id, boolean documentVerified) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setDocumentVerified(documentVerified);
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setCompanyName(companyDetails.getCompanyName());
        company.setIndustry(companyDetails.getIndustry());
        company.setCompanySize(companyDetails.getCompanySize());
        company.setLocation(companyDetails.getLocation());
        company.setWebsite(companyDetails.getWebsite());
        company.setEmail(companyDetails.getEmail());
        company.setPhone(companyDetails.getPhone());
        company.setDescription(companyDetails.getDescription());
        company.setCompanyLogo(companyDetails.getCompanyLogo());
        company.setFileUrl(companyDetails.getFileUrl());

        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        companyRepository.delete(company);
    }
}
