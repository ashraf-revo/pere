package org.revo.pere.repository;

import org.revo.pere.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Page<Company> findAllByEmailLikeOrNameLike(String email, String name, Pageable pageable);

    Page<Company> findAllBy(Pageable pageable);

    Optional<Company> findByEmail(String email);
}
