package hr.tvz.java.teambuildingbooking.repository;

import hr.tvz.java.teambuildingbooking.model.Role;
import hr.tvz.java.teambuildingbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    @Modifying
    @Query("update User u set u.username = ?1, u.name = ?2, u.surname = ?3 , u.email = ?4, u.telephone = ?5, u.dateOfBirth = ?6 where u.username = ?7")
    void editUser(String username, String name, String surname, String email, String telephone, Date dateOfBirth, String currentUserUsername);

    @Query("select u.roles from User u where u.username = ?1")
    Set<Role> findRolesByUsername(String username);

}
