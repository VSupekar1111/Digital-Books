package com.digitalbooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b WHERE b.title= :title AND b.authorId= :authorId")
	List<Book> getBooksByAuthor(@Param("title") String title, @Param("authorId") Long authorID);

	@Query("SELECT b FROM Book b WHERE b.category= :category")
	List<Book> findByCategory(@Param("category") String category);

	@Query("SELECT b FROM Book b WHERE b.title= :title")
	List<Book> findByTitle(@Param("title") String title);

	@Query("SELECT b FROM Book b WHERE b.authorId= :authorId")
	List<Book> findByAuthorId(@Param("authorId") Long authorId);

	@Query("SELECT b FROM Book b WHERE b.price= :price")
	List<Book> findByPrice(@Param("price") String price);

	@Query("SELECT b FROM Book b WHERE b.publisher= :publisher")
	List<Book> findByPublisher(@Param("publisher") String publisher);

}
