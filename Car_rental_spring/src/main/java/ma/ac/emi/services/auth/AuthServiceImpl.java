    package ma.ac.emi.services.auth;

    import jakarta.annotation.PostConstruct;
    import ma.ac.emi.dto.SignupRequest;
    import ma.ac.emi.dto.UserDto;
    import ma.ac.emi.entity.User;
    import ma.ac.emi.enums.UserRole;
    import ma.ac.emi.repository.UserRepository;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;


        public AuthServiceImpl(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @PostConstruct
        public void createAdminAccount(){
            User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
            if(adminAccount == null){
                User newAdminAccount = new User();
                newAdminAccount.setName("Admin");
                newAdminAccount.setEmail("admin@admin.com");
                newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
                newAdminAccount.setUserRole(UserRole.ADMIN);
                userRepository.save(newAdminAccount);
                System.out.println("Admin account created");

            }
        }


        @Override
        public UserDto createCustomer(SignupRequest signupRequest) {
            User user = new User();
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
            user.setUserRole(UserRole.CUSTOMER);
            User createdUser =userRepository.save(user);
            UserDto userDto = new UserDto();
            userDto.setId(createdUser.getId());
            return userDto;
        }

        @Override
        public boolean hasCustomerWithEmail(String email) {
            return userRepository.findFirstByEmail(email).isPresent();
        }
    }
