package ma.ac.emi.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.ac.emi.dto.BookACarDto;
import ma.ac.emi.dto.CarDto;
import ma.ac.emi.dto.SearchCarDto;
import ma.ac.emi.services.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerControler {

    private final CustomerService customerService;
    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> carDtoList = customerService.getAllCars();
        return ResponseEntity.ok(carDtoList);
    }

    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto) {
        boolean success = customerService.bookACar(bookACarDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable long carId) {
        CarDto carDto = customerService.getCarById(carId);
        if (carDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(carDto);
        }

    }

   @GetMapping("/car/booking/{userId}")
  public ResponseEntity<List<BookACarDto>> getBookingsByUserId(@PathVariable Long userId){
    return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
  }

  @PostMapping("/car/search")
  public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
    return ResponseEntity.ok(customerService.searchCar(searchCarDto));
  }
}
