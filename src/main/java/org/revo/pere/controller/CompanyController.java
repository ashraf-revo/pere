package org.revo.pere.controller;

import org.revo.pere.service.CompanyService;
import org.revo.pere.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("{id}")
    public ResponseEntity<Optional<Company>> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.findOne(id));
    }

    @GetMapping("findAll")
    public ResponseEntity<Iterable<Company>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping
    public ResponseEntity<Company> save(@RequestBody @Valid Company company) {
        company.setId(null);
        Company save = companyService.save(company);
        return save.getId() != null ? ResponseEntity.ok(save) : ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        return companyService.findOne(id).map(it -> ResponseEntity.ok(companyService.save(it, company)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return companyService.findOne(id).map(it -> {
            companyService.delete(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }
}
