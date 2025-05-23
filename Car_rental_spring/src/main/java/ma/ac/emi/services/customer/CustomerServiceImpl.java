package ma.ac.emi.services.customer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.ac.emi.dto.BookACarDto;
import ma.ac.emi.dto.CarDto;
import ma.ac.emi.dto.CarDtoListDto;
import ma.ac.emi.dto.SearchCarDto;
import ma.ac.emi.entity.BookACar;
import ma.ac.emi.entity.Car;
import ma.ac.emi.entity.User;
import ma.ac.emi.enums.BookCarStatus;
import ma.ac.emi.repository.BookACarRepository;
import ma.ac.emi.repository.CarRepository;
import ma.ac.emi.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookACarRepository bookACarRepository;




    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACarDto bookACarDto) {
        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            Car existingCar = optionalCar.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            long diffInMiliSoconde = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMiliSoconde);
            bookACar.setDays(days);
            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());
            bookACar.setPrice(existingCar.getPrice()*days);
            bookACarRepository.save(bookACar);
            return true;

        }
        return false;
    }

    @Override
    public CarDto getCarById(long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

  @Override
  public List<BookACarDto> getBookingsByUserId(Long userId) {
    return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDTO).collect(Collectors.toList());
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
