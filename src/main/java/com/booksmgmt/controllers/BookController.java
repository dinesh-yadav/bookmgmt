package com.booksmgmt.controllers;

import com.booksmgmt.entities.Book;
import com.booksmgmt.services.BookService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Mono<Book> addBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping
    public Flux<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{bookId}")
    public Mono<Book> getBook(@PathVariable int bookId) {
        return bookService.get(bookId);
    }

    @PutMapping("/{bookId}")
    public Mono<Book> updateBook(@PathVariable int bookId, @RequestBody Book book) {
        return bookService.update(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    public Mono<Void> deleteBook(@PathVariable int bookId) {
        return bookService.delete(bookId);
    }
}
