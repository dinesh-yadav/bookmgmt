package com.booksmgmt.repositories;

import com.booksmgmt.entities.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Integer> {
    //for getting some value with column other then id or multiple conditions
    // we can use findBy functions.
    Mono<Book> findByName(String name);
    Mono<Book> findByBookIdAndName(int bookId, String name);
    Flux<Book> findByBookIdOrName(int bookId, String name);
    Flux<Book> findByPublisher(String publisher);
    Flux<Book> findByNameAndAuthor(String name,String author);

    //for getting results using complex queries,
    // we can use Query annotation.

    @Query("select * from book_details where name = :name AND author = :auth")
    Flux<Book> getAllBooksByAuthor(String name, @Param("auth") String author);

    @Query("select * from book_details where name  LIKE :title")
    Flux<Book> searchBookByTitle(String title);
}
