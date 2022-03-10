package com.thuydieutran.admin.user;

import com.thuydieutran.kidshopcommon.entities.Role;
import com.thuydieutran.kidshopcommon.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userThuy = new User("thuy@gmail.com", "thuypass", "Thuy", "Dieu");
        userThuy.addRole(roleAdmin);
        User savedUser = userRepository.save(userThuy);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserTwoRoles() {
        Role roleEditor = new Role(3);
        Role roleAdmin = new Role(1);
        User userLily = new User("lila@gmail.com", "lilapass", "Lily", "Tran");
        userLily.addRole(roleEditor);
        userLily.addRole(roleAdmin);
        User savedUser = userRepository.save(userLily);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User userThuy = userRepository.findById(1).get();
        System.out.println(userThuy);
        assertThat(userThuy).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userThuy = userRepository.findById(1).get();
        userThuy.setEnable(true);
        userThuy.setEmail("thuydieu@email.com");
        userRepository.save(userThuy);
    }

    @Test
    public void testUpdateUserRoles() {
        User userLily = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesman = new Role(2);
        userLily.getRoles().remove(roleEditor);
        userLily.addRole(roleSalesman);
        userRepository.save(userLily);
    }

    @Test
    public void testDeleteUser() {
       Integer userId = 2;
       userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "ben@gmail.com";
        User user = userRepository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = userRepository.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser() {
       Integer id = 1;
       userRepository.updateEnableStatus(id, false);
    }

    @Test
    public void testEnableUser() {
        Integer id = 2;
        userRepository.updateEnableStatus(id, true);
    }
}
