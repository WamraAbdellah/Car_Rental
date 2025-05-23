package ma.ac.emi.services.customer;

import ma.ac.emi.dto.BookACarDto;
import ma.ac.emi.dto.CarDto;
import ma.ac.emi.dto.CarDtoListDto;
import ma.ac.emi.dto.SearchCarDto;

import java.util.List;

public interface CustomerService {
    List<CarDto> getAllCars();
    boolean bookACar(BookACarDto bookACarDto);

    CarDto getCarById(long carId);

    List<BookACarDto> getBookingsByUserId(Long userId);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
