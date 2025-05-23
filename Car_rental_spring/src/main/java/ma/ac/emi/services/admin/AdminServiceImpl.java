package ma.ac.emi.services.admin;

import lombok.RequiredArgsConstructor;
import ma.ac.emi.dto.BookACarDto;
import ma.ac.emi.dto.CarDto;
import ma.ac.emi.dto.CarDtoListDto;
import ma.ac.emi.dto.SearchCarDto;
import ma.ac.emi.entity.BookACar;
import ma.ac.emi.entity.Car;
import ma.ac.emi.enums.BookCarStatus;
import ma.ac.emi.repository.BookACarRepository;
import ma.ac.emi.repository.CarRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author HP
 **/
@Service

public class AdminServiceImpl implements AdminService {

    private final CarRepository carRepository;

    private final BookACarRepository bookACarRepository;

    public AdminServiceImpl(CarRepository carRepository, BookACarRepository bookACarRepository) {
        this.carRepository = carRepository;
      this.bookACarRepository = bookACarRepository;
    }

    @Override
    public boolean postCar(CarDto carDto) throws IOException {
        try {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice(carDto.getPrice());
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());
            car.setImage(carDto.getImage().getBytes());

            carRepository.save(car);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public boolean updateCar(Long id, CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            if (carDto.getImage() != null) {
                existingCar.setImage(carDto.getImage().getBytes());
            }
            existingCar.setName(carDto.getName());
            existingCar.setBrand(carDto.getBrand());
            existingCar.setColor(carDto.getColor());
            existingCar.setPrice(carDto.getPrice());
            existingCar.setYear(carDto.getYear());
            existingCar.setType(carDto.getType());
            existingCar.setDescription(carDto.getDescription());
            existingCar.setTransmission(carDto.getTransmission());
            carRepository.save(existingCar);
            return true;
        }
        return false;
    }

  @Override
  public List<BookACarDto> getBookings() {
    return bookACarRepository.findAll().stream().map(BookACar::getBookACarDTO).collect(Collectors.toList());
  }

  @Override
  public boolean changeBookingStatus(Long bookingId, String status) {
      Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingId);
      if (optionalBookACar.isPresent()) {
        BookACar existingBookACar = optionalBookACar.get();
        if (Objects.equals(status,"Approve"))
          existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
        else
          existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);
        bookACarRepository.save(existingBookACar);
        return true;
      }
    return false;
  }

  @Override
  public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
      Car car = new Car();
      car.setBrand(searchCarDto.getBrand());
      car.setColor(searchCarDto.getColor());
      car.setType(searchCarDto.getType());
      car.setTransmission(searchCarDto.getTransmission());
    ExampleMatcher exampleMatcher =
      ExampleMatcher.matchingAll()
        .withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    Example<Car> carExample = Example.of(car, exampleMatcher);
    List<Car> carList = carRepository.findAll(carExample);
    CarDtoListDto carDtoListDto = new CarDtoListDto();
    carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));
    return carDtoListDto;
  }


}
