package com.netcracker.controller.crud;

import com.netcracker.dto.BookDTO;
import com.netcracker.model.Book;
import com.netcracker.repos.BookRepository;
import com.netcracker.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "book/")
public class BookController {
    private BookService bookService;
    private BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @PatchMapping(value = "patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> patchbook(@PathVariable(value = "id", required = true) Long id,
                                             @RequestBody BookDTO updates) {

        Book book = bookService.findById(id);
        if (book == null) {
            return new ResponseEntity<>("book not found", HttpStatus.BAD_REQUEST);
        } else {
            bookService.update(updates, book);
            bookRepository.save(book);
            return ResponseEntity.ok("recourse updated");
        }
    }

    @PostMapping(value = "add")
    public ResponseEntity<String> addbook(@RequestBody Book book) {
        if (bookRepository.findByName(book.getName()) != null) {
            return new ResponseEntity("book with name " + book.getName() + " already registered", HttpStatus.BAD_REQUEST);
        }
        bookRepository.save(book);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> patchbook(@PathVariable(value = "id", required = true) Long id) {

        if (bookService.findById(id) == null)
            return new ResponseEntity("book does not exist", HttpStatus.BAD_REQUEST);
        else {
            bookService.delete(bookService.findById(id));
            return ResponseEntity.ok("book successfully deleted");
        }
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping(value = "getById/{id}")
    public ResponseEntity<Book> getAll(@PathVariable(value = "id", required = true) Long id) {

        if (bookService.findById(id) == null)
            return new ResponseEntity("book does not exist", HttpStatus.BAD_REQUEST);
        else {
            return ResponseEntity.ok(bookService.findById(id));
        }
    }

    @PutMapping(value = "rewrite/{id}")
    public ResponseEntity<String> rewriteEntity(@PathVariable(value = "id", required = true) Long id,
                                                @RequestBody Book bookNew) {
        Book bookOld = bookService.findById(id);
        if (bookOld == null)
            return new ResponseEntity("book does not exist", HttpStatus.BAD_REQUEST);
        bookService.change(bookOld, bookNew);
        bookRepository.save(bookOld);
        return ResponseEntity.ok("Data successfully changed");
    }
}

