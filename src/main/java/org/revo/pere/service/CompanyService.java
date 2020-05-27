package org.revo.pere.service;

import org.revo.pere.domain.Company;
import org.revo.pere.model.Search;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Iterable<Company> findAll();

    Company save(Company company);

    Optional<Company> findOne(Long id);

    Company save(Long id, Company company);

    void delete(Long id);

    Page<Company> findAllBy(Search pageable);
}
