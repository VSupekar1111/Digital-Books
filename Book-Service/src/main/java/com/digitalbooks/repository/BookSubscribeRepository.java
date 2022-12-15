package com.digitalbooks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Book;
import com.digitalbooks.model.BookSubscribe;

@Repository
public interface BookSubscribeRepository extends JpaRepository<BookSubscribe, Long> {

	@Query("SELECT b.id FROM BookSubscribe b WHERE b.book= :book AND b.userId= :reader AND b.isActive= :isActive")
	List<Long> findBySubscribedBookAndReader(@Param("book")Book book,@Param("reader") Long reader,@Param("isActive") boolean iaActive);

	@Query("SELECT bs FROM BookSubscribe bs WHERE bs.userId= :reader AND bs.isActive= :isActive")
	List<BookSubscribe> findSubscribedBooksByReader(@Param("reader") Long reader,@Param("isActive") boolean iaActive);
   
	@Query("SELECT b FROM BookSubscribe b WHERE b.subscribeId= :subscribeId AND b.userId= :reader")
	Optional<BookSubscribe> findBySubscriptionIdAndReader(@Param("subscribeId") Long subscribeId,@Param("reader")  Long readerId);


}
