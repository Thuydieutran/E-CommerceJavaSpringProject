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
        Role roleAssistant = new Role(5);
        User userLily = new User("lily@gmail.com", "lilypass", "Lily", "Tran");
        userLily.addRole(roleEditor);
        userLily.addRole(roleAssistant);
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
}
