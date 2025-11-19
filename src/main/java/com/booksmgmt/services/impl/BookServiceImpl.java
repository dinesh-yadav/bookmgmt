package com.booksmgmt.services.impl;

import com.booksmgmt.entities.Book;
import com.booksmgmt.repositories.BookRepository;
import com.booksmgmt.services.BookService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Book> create(Book book) {
        // for learning reactive programming, add some prints
        System.out.println(Thread.currentThread().getName());
        Mono<Book> bookMono = bookRepository.save(book).doOnNext( data -> {
            System.out.println(Thread.currentThread().getName());
            }
        );
        return bookMono;
    }

    @Override
    public Flux<Book> getAll() {
        //adding delay to see that api calls are asynchronous
        // and we can cancel also in between.
        return bookRepository
                .findAll()
                .delayElements(Duration.ofSeconds(2))
                .log()
                .map(book -> {
                    book.setName(book.getName().toUpperCase());
                    return book;
                });
    }

    @Override
    public Mono<Book> get(int bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Mono<Book> update(int bookId, Book book) {
        Mono<Book> oldBook = bookRepository.findById(bookId);
        return oldBook.flatMap(book1 -> {
            book1.setAuthor(book.getAuthor());
            book1.setName(book.getName());
            book1.setPublisher(book.getPublisher());
            book1.setDescription(book.getDescription());
            return bookRepository.save(book1);
        });
    }

    @Override
    public Mono<Void> delete(int bookId) {
        return bookRepository
                .findById(bookId)
                .flatMap(book -> bookRepository.delete(book));
    }

    @Override
    public Flux<Book> search(String query) {
        return null;
    }
}
