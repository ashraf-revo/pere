package org.revo.pere.controller;

import org.revo.pere.domain.Company;
import org.revo.pere.model.Search;
import org.revo.pere.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Pattern;

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
        Company save = companyService.save(id, company);
        return save.getId() != null ? ResponseEntity.ok(save) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return companyService.findOne(id).map(it -> {
            companyService.delete(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }

    private Pattern compile = Pattern.compile(".+_(DO|RA|FLEET)-[0-9]+");
    private WebClient webClient = WebClient.create("https://api.github.com/repos/elmenus/branches-restrictions-POC/git");

    @PostMapping("call")
    public Mono<String> call(@RequestBody Call call) {

        if (call.getRef().startsWith("refs/heads")) {
            boolean matches = compile.matcher(call.getRef()).matches();
            if (matches) {
                return Mono.just("Branch name matches the regex");
            } else {
                return webClient.delete()
                        .uri(call.getRef())
                        .header("Authorization", "Basic YXNocmFmLWF0ZWY6ZWYwZGEzN2U2NjRmNDJhNmZiZDkzYjA0ZGNiZWY4YTNhYzc2ZjE3YQ==")
                        .exchange().map(it -> "deleted");
            }
        } else
            return Mono.just("Not a branch");
    }
}

class Call {
    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
