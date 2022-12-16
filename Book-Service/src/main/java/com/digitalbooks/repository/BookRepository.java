package com.digitalbooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b.id FROM Book b WHERE b.title= :title AND b.authorId= :authorId")
	List<Long> getBooksByAuthor(@Param("title") String title, @Param("authorId") Long authorID);

	@Query("SELECT b FROM Book b WHERE b.category= :category AND b.active =:active")
	List<Book> findByCategory(@Param("category") String category, @Param("active") boolean active);

	@Query("SELECT b FROM Book b WHERE b.title= :title AND b.active =:active")
	List<Book> findByTitle(@Param("title") String title, @Param("active") boolean active);

	@Query("SELECT b FROM Book b WHERE b.authorId= :authorId AND b.active =:active")
	List<Book> findByAuthorId(@Param("authorId") Long authorId, @Param("active") boolean active);

	@Query("SELECT b FROM Book b WHERE b.price= :price AND b.active =:active")
	List<Book> findByPrice(@Param("price") String price, @Param("active") boolean active);

	@Query("SELECT b FROM Book b WHERE b.publisher= :publisher AND b.active =:active")
	List<Book> findByPublisher(@Param("publisher") String publisher, @Param("active") boolean active);

	@Query("SELECT b FROM Book b WHERE b.authorId= :authorId")
	List<Book> findAllByAuthorId(@Param("authorId") Long authorId);

}
