package org.revo.pere.service.impl;

import org.revo.pere.domain.Company;
import org.revo.pere.model.Search;
import org.revo.pere.repository.CompanyRepository;
import org.revo.pere.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        company.setId(null);
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findOne(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company save(Long id, Company company) {
        return findOne(id).map(it -> {
            company.setId(id);
            return companyRepository.save(company);
        }).orElse(company);
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Page<Company> findAllBy(Search search) {
        if (search.getProperty() != null && search.getValue() != null && !search.getValue().trim().equals("") && search.getDirection() != null) {
            PageRequest of = PageRequest.of(search.getPage(), search.getSize(), Sort.by(search.getDirection(), search.getProperty().name()));
            return companyRepository.findAllByEmailLikeOrNameLike(search.getValue().trim(), search.getValue().trim(), of);
        }
        if (search.getValue() != null && !search.getValue().trim().equals("")) {
            PageRequest of = PageRequest.of(search.getPage(), search.getSize());
            return companyRepository.findAllByEmailLikeOrNameLike(search.getValue().trim(), search.getValue().trim(), of);
        }
        if (search.getProperty() != null) {
            PageRequest of = PageRequest.of(search.getPage(), search.getSize(), Sort.by(search.getDirection(), search.getProperty().name()));
            return companyRepository.findAllBy(of);
        }
        return companyRepository.findAllBy(PageRequest.of(search.getPage(), search.getSize()));
    }
}
