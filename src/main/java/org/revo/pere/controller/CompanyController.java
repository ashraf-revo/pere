package org.revo.pere.controller;

import org.revo.pere.domain.Company;
import org.revo.pere.model.Search;
import org.revo.pere.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Company>> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.findOne(id));
    }

    @GetMapping("findAll")
    public ResponseEntity<Iterable<Company>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping("search")
    public ResponseEntity<Page<Company>> search(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(companyService.findAllBy(search));
    }

    @PostMapping
    public ResponseEntity<Company> save(@RequestBody @Valid Company company) {
        Company save = companyService.save(company);
        return save.getId() != null ? ResponseEntity.ok(save) : ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody @Valid Company company) {
        Company save = companyService.save(id,company);
        return save.getId() != null ? ResponseEntity.ok(save) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return companyService.findOne(id).map(it -> {
            companyService.delete(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }
    
    
    
        @PostMapping("sf")
    public ResponseEntity<String> saveee(@RequestBody @String sf) {
        return sf;
    }

    
}
