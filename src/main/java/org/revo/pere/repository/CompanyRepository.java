package org.revo.pere.repository;

import org.revo.pere.domain.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAllByEmailLikeOrNameLike(String email, String name, Pageable pageable);

    List<Company> findAllBy(Pageable pageable);
}
