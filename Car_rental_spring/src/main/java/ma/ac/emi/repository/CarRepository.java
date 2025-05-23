package ma.ac.emi.repository;

import ma.ac.emi.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author HP
 **/
@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

}
