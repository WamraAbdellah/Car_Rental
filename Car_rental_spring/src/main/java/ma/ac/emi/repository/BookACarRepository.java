package ma.ac.emi.repository;

import ma.ac.emi.entity.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar, Long> {
  List<BookACar> findAllByUserId(Long userId);
}
