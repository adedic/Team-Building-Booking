package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.mapper.UserMapper;
import hr.tvz.java.teambuildingbooking.model.Role;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.EditUserForm;
import hr.tvz.java.teambuildingbooking.model.form.RegistrationForm;
import hr.tvz.java.teambuildingbooking.repository.RoleRepository;
import hr.tvz.java.teambuildingbooking.repository.UserRepository;
import hr.tvz.java.teambuildingbooking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE_NAME = "ROLE_USER";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
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

    @Transactional
    @Override
    public int editUser(EditUserForm editUserForm, String currentUserUsername) throws ParseException {
        User user = UserMapper.INSTANCE.editUserFormToUser(editUserForm);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date dateOfBirth = simpleDateFormat.parse(editUserForm.getDateOfBirth());
        user.setDateOfBirth(dateOfBirth);

        int i = userRepository.editUser(user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getTelephone(), user.getDateOfBirth(), currentUserUsername);
        return i;
    }

    @Override
    public boolean existsByUsernameIgnoreCase(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Set<Role> findRolesByUsername(String username) {
        return userRepository.findRolesByUsername(username);
    }

    @Override
    public boolean hasRole(String username, String role) {
        Set<Role> roles = userRepository.findRolesByUsername(username);

        for (Role r : roles) {
            if (r.getName().equals(role)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
