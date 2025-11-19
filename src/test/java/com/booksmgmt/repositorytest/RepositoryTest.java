package com.booksmgmt.repositorytest;

import com.booksmgmt.entities.Book;
import com.booksmgmt.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findMethodTest() {
        Mono<Book> nameMono = bookRepository.findByName("Algebra");
        StepVerifier.create(nameMono)
                .expectNextCount(1)
                .verifyComplete();

        Flux<Book> authorFlux = bookRepository.findByAuthor("Tom");
        StepVerifier.create(authorFlux)
                .expectNextCount(1)
                .verifyComplete();

        bookRepository.findByNameAndAuthor("Algebra", "O.P.Sharma")
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();


    }


    @Test
    public void queryMethodsTest() {
        bookRepository.getAllBooksByAuthor("Physics","Hari")
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        bookRepository.searchBookByTitle("%vance%")
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

    }

}
