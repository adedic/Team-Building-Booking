package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.UserMapper;
import hr.tvz.java.teambuildingbooking.model.Role;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.repository.RoleRepository;
import hr.tvz.java.teambuildingbooking.repository.UserRepository;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE_NAME = "ROLE_USER";

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(RegistrationForm registrationForm) throws ParseException {
        User user = UserMapper.INSTANCE.registrationFormToUser(registrationForm);

        user.setPassword(new BCryptPasswordEncoder().encode(registrationForm.getPassword()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = simpleDateFormat.parse(registrationForm.getDateOfBirth());
        user.setDateOfBirth(dateOfBirth);

        user.setDateOfRegistration(new Date());

        Role selectedRole = roleRepository.findByName(registrationForm.getUserRole());
        Role defaultUserRole = roleRepository.findByName(DEFAULT_USER_ROLE_NAME);
        Set<Role> roleSet = new HashSet<>();

        roleSet.add(selectedRole);
        roleSet.add(defaultUserRole);
        user.setRoles(roleSet);
        user.setEnabled(true);

        log.info("---> Saving new User with username = " + user.getUsername() + " to database ...");
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsernameIgnoreCase(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
