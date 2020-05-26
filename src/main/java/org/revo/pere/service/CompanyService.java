package org.revo.pere.service;

import org.revo.pere.domain.Company;

import java.util.Optional;

public interface CompanyService {
    Iterable<Company> findAll();

    Company save(Company company);

    Optional<Company> findOne(Long id);

    Company save(Company current, Company company);

    void delete(Long id);
}
