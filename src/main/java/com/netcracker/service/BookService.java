package com.netcracker.service;


import com.netcracker.dto.BookDTO;
import com.netcracker.model.Book;
import com.netcracker.repos.BookRepository;
import com.netcracker.view.book.BookView;
import com.netcracker.view.book.BookViewWithNamesAndPrices;
import com.netcracker.view.book.BookViewWithQuantity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book findById(Long id){
        Book book = bookRepository.findById(id).orElse(null);
        log.info("IN findById - id {}, book {}",id,book);
        return book;
    }

    public void delete(Book book){
        bookRepository.delete(book);
        log.info("Successfully deleted {}:",book);
    }

    public void update(BookDTO bookDTO, Book book){
        if (bookDTO.getName()!=null) book.setName(bookDTO.getName());
        if (bookDTO.getPrice()!=null) book.setPrice(bookDTO.getPrice());
        if (bookDTO.getQuantity()!=null) book.setQuantity(bookDTO.getQuantity());
        if (bookDTO.getStorage()!=null) book.setStorage(bookDTO.getStorage());
    }
    public void change(Book bookOld,Book bookNew){
        bookOld.setName(bookNew.getName());
        bookOld.setPrice(bookNew.getPrice());
        bookOld.setQuantity(bookNew.getQuantity());
        bookOld.setStorage(bookNew.getStorage());
    }

    public List<Book> getAllBooks(){
        List<Book> books =bookRepository.findAll();
        log.info("IN getAllBooks - books: {}",books);
        return books;
    }

    public List<BookView> getAllDistinctBooks(){
        List<BookView> list = bookRepository.getAllDistinctByNameAndPrice();
        log.info("IN getAllDistinctBooks - name and price :{}",list);
        return list;
    }

    public List<BookViewWithNamesAndPrices> getBooksNamesAndPrices(Double price){
        List<BookViewWithNamesAndPrices> list = bookRepository.getBooksNamesAndPrices(price);
        log.info("IN getBookNamesAndPrices - name and price :{}",list);
        return list;
    }

    public List<BookViewWithQuantity> getDifficultBooksInfo(Integer quantity){
        return bookRepository.findAllBooksInfo(quantity);
    }

    public List<BookView> getBookNameById(){
        return bookRepository.getBooksById();
    }
}

