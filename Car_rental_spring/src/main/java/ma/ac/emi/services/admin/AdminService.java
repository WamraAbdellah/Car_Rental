package ma.ac.emi.services.admin;

import ma.ac.emi.dto.BookACarDto;
import ma.ac.emi.dto.CarDto;
import ma.ac.emi.dto.CarDtoListDto;
import ma.ac.emi.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

/**
 * author HP
 **/
public interface AdminService {
    boolean postCar(CarDto CarDto) throws IOException;
    List<CarDto> getAllCars();
    void deleteCar(Long id);

    CarDto getCarById(Long id);

    boolean updateCar(Long id, CarDto carDto) throws IOException;

    List<BookACarDto> getBookings();

    boolean changeBookingStatus(Long bookingId, String status);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
