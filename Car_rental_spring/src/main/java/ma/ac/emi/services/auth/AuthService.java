package ma.ac.emi.services.auth;

import ma.ac.emi.dto.SignupRequest;
import ma.ac.emi.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);
    boolean hasCustomerWithEmail(String email);
}
