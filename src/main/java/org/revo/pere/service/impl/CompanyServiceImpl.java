package org.revo.pere.service.impl;

import org.revo.pere.repository.CompanyRepository;
import org.revo.pere.service.CompanyService;
import org.revo.pere.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findOne(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company save(Company current, Company company) {
        return null;
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }
}
