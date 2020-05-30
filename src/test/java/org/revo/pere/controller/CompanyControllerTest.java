package org.revo.pere.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.revo.pere.domain.Company;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerTest {
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void findOne() {
        webTestClient.get().uri("/api/v1/company/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Company.class).consumeWith(it -> {
            assertThat(it.getResponseBody(), notNullValue());
            assertThat(it.getResponseBody().getId(), is(1L));
            assertThat(it.getResponseBody().getEmail(), containsString("mail.com"));
        });
    }

    @Test
    void findAll() {
        webTestClient.get().uri("/api/v1/company/findAll").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Company.class)
                .consumeWith(it -> {
                    assertThat(it.getResponseBody(), notNullValue());
                    assertThat(it.getResponseBody(), hasSize(greaterThan(0)));
                });
    }

    @Test
    void successWhenSaveCompany() {
        Company company = new Company();
        company.setName("unknown");
        company.setEmail("unknown@gmail.com");
        webTestClient.post().uri("/api/v1/company").bodyValue(company).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Company.class).consumeWith(it -> {
            assertThat(it.getResponseBody(), notNullValue());
            assertThat(it.getResponseBody().getId(), notNullValue());
            assertThat(it.getResponseBody().getName(), is(company.getName()));
            assertThat(it.getResponseBody().getEmail(), is(company.getEmail()));
        });
    }

    @Test
    void failWhenSaveCompanyWithExistEmail() {
        Company company = new Company();
        company.setName("unknown");
        company.setEmail("ashraf@gmail.com");
        webTestClient.post().uri("/api/v1/company").bodyValue(company).exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Map.class).consumeWith(it -> {
            assertThat(it.getResponseBody(), notNullValue());
            assertThat(it.getResponseBody().containsKey("email"), is(true));
            assertThat(it.getResponseBody().get("email"), is("already exist"));
        });
    }
    @Test
    void failWhenSaveCompanyValidationFail() {
        Company company = new Company();
        company.setName("un");
        company.setEmail("ashraf.gmail.com");
        webTestClient.post().uri("/api/v1/company").bodyValue(company).exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Map.class).consumeWith(it -> {
            assertThat(it.getResponseBody(), notNullValue());
            assertThat(it.getResponseBody().containsKey("email"), is(true));
            assertThat(it.getResponseBody().get("email"), is("must be a well-formed email address"));
            assertThat(it.getResponseBody().get("name"), is("size must be between 3 and 20"));
        });
    }

    @Test
    void successWhenUpdate() {
        Company company = new Company();
        company.setName("unknown");
        company.setEmail("unknown"+((int) (Math.random() * 100))+"@gmail.com");
        webTestClient.put().uri("/api/v1/company/2").bodyValue(company).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Company.class).consumeWith(it -> {
            assertThat(it.getResponseBody(), notNullValue());
            assertThat(it.getResponseBody().getId(), notNullValue());
            assertThat(it.getResponseBody().getName(), is(company.getName()));
            assertThat(it.getResponseBody().getEmail(), is(company.getEmail()));
        });
    }

    @Test
    void failWhenUpdateWithExistEmail() {
        Company company = new Company();
        company.setName("unknown");
        company.setEmail("ashraf@gmail.com");
        webTestClient.put().uri("/api/v1/company/2").bodyValue(company).exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Map.class).consumeWith(it -> {
            assertThat(it.getResponseBody(), notNullValue());
            assertThat(it.getResponseBody().containsKey("email"), is(true));
            assertThat(it.getResponseBody().get("email"), is("already exist"));
        });
    }

    @Test
    void delete() {
        webTestClient.delete().uri("/api/v1/company/3").exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}
