package ma.ac.emi.dto;

import lombok.Data;
import ma.ac.emi.enums.BookCarStatus;

import java.util.Date;

@Data
public class BookACarDto {
    private long id;
    private Date fromDate;
    private Date toDate;
    private long days;
    private long price;
    private BookCarStatus bookCarStatus;
    private long carId;
    private long userId;
    private String username;
    private String email;
}
