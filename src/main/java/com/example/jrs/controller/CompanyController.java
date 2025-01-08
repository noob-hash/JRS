package com.example.jrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.entity.Company;
import com.example.jrs.service.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("jrs/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/save")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company savedCompany = companyService.createCompany(company);
        return ResponseEntity.status(201).body(savedCompany);
    }

    @GetMapping("/getAll")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody Company company) {
        Company updatedCompany = companyService.updateCompany(id, company);
        return ResponseEntity.ok(updatedCompany);
    }

    @PutMapping("/{id}/verify-document")
    public ResponseEntity<Company> verifyDocument(
            @PathVariable Long id,
            @RequestBody Boolean documentVerified) {
        Company updatedCompany = companyService.toggleDocumentVerification(id, documentVerified);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
