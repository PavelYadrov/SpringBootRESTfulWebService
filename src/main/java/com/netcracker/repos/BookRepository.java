package com.netcracker.repos;

import com.netcracker.dto.BookDTO;
import com.netcracker.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByName(String name);


    @Query(value = "select distinct b.name,b.price from book b",nativeQuery = true)
    List<Object[]> getAllDistinctByNameAndPrice();

    @Query(value = "select b.name,b.price from book b where b.price>:price or b.name like '%Windows%'\n" +
            "order by b.name,b.price desc;",nativeQuery = true)
    List<Object[]> getBooksNamesAndPrices(@Param("price")Double price);

    @Query(value = "select b.name,b.storage,b.quantity,b.price from book b \n" +
            "join purchase pu on (b.id=pu.book_id)\n" +
            "join shop s on(s.id=pu.shop_id)\n" +
            "where s.location=b.storage and b.quantity>=:quantity",nativeQuery = true)
    List<Object[]> findAllBooksInfo(@Param("quantity") Integer quan);
}
