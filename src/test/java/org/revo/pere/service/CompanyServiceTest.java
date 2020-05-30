package org.revo.pere.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.revo.pere.domain.Company;
import org.revo.pere.execption.EmailExistException;
import org.revo.pere.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CompanyService.class))
class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test
    void findAll() {
        List<Company> companies = StreamSupport.stream(companyService.findAll().spliterator(), false).collect(Collectors.toList());
        /*testing default company*/
        assertThat(companies, hasSize(greaterThan(0)));
        assertThat(companies, everyItem(hasProperty("email", notNullValue())));
        assertThat(companies, everyItem(hasProperty("name", notNullValue())));
    }

    @Test
    void saveThrowExceptionWhenSaveExistEmail() {
        Company company = new Company();
        company.setEmail("revo@gmail.com");
        company.setName("revo");
        Assertions.assertThrows(EmailExistException.class, () -> {
            companyService.save(company);
        });
    }

    @Test
    void saveSuccessWhenSaveRandomEmail() {
        Company company = new Company();
        company.setEmail("ashraf" + ((int) (Math.random() * 100)) + "@gmail.com");
        company.setName("revo");
        Company save = companyService.save(company);
        assertThat(save.getId(), notNullValue());
        assertThat(save.getId(), greaterThan(0L));
        Optional<Company> one = companyService.findOne(save.getId());
        assertThat(one.isPresent(), is(true));
        assertThat(one.get(), is(notNullValue()));
        assertThat(one.get().getEmail(), is(company.getEmail()));
        assertThat(one.get().getName(), is(company.getName()));
    }

    @Test
    void findOneAlreadyExist() {
        /* test find default one */
        Optional<Company> one = companyService.findOne(1L);
        assertThat(one.isPresent(), is(true));
        assertThat(one.get(), notNullValue());
        assertThat(one.get().getEmail(), notNullValue());
        assertThat(one.get().getName(), notNullValue());
    }

    @Test
    void findOneNotExist() {
        /* test find default one */
        Optional<Company> one = companyService.findOne(1001L);
        assertThat(one.isPresent(), is(false));
    }

    @Test
    void updateThrowExceptionWhenSaveExistEmail() {
        Company company = new Company();
        company.setEmail("revo@gmail.com");
        company.setName("revo");
        Assertions.assertThrows(EmailExistException.class, () -> {
            companyService.save(1L, company);
        });
    }

    @Test
    void updateSuccessWhenSaveCurrentEmail() {
        Company company = new Company();
        company.setEmail("ashraf@gmail.com");
        company.setName("revo");
        companyService.save(1L, company);
        Optional<Company> one = companyService.findOne(1L);
        assertThat(one.isPresent(), is(true));
        assertThat(one.get(), is(notNullValue()));
        assertThat(one.get().getEmail(), is(company.getEmail()));
        assertThat(one.get().getName(), is(company.getName()));
    }

    @Test
    void delete() {
        assertThat(companyService.findOne(2L).isPresent(), is(true));
        companyService.delete(2L);
        assertThat(companyService.findOne(2L).isPresent(), is(false));
    }

    @Test
    void findAllBySimplePageable() {
        Search pageable = new Search();
        pageable.setSize(5);
        Page<Company> allBy = companyService.findAllBy(pageable);
        assertThat(allBy.getTotalElements(), greaterThan(0L));
        assertThat(allBy.getContent(), hasSize(lessThanOrEqualTo(5)));
        /*we test default data*/
        assertThat(allBy.getContent(), hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    void findAllSearchWithValue() {
        Search pageable = new Search();
        pageable.setSize(5);
        pageable.setValue("ash");
        Page<Company> allBy = companyService.findAllBy(pageable);
        assertThat(allBy.getTotalElements(), greaterThan(0L));
        assertThat(allBy.getContent(), hasSize(lessThanOrEqualTo(pageable.getSize())));
        /*we test default data*/
        assertThat(allBy.getContent(), hasSize(greaterThanOrEqualTo(1)));
        assertThat(allBy.getContent(), everyItem(anyOf(
                hasProperty("email", containsString(pageable.getValue())),
                hasProperty("name", containsString(pageable.getValue()))
        )));

    }
}
