package com.thuydieutran.admin.user;

import com.thuydieutran.kidshopcommon.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;

// This annotation is used to leverage the Data JPA test of Spring Data JPA
@DataJpaTest
// This annotation is used if we want to test with the real database which means we need to override the default configuration
// Because by default, Spring Data JPA will run the test against an in-memory database
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
// To commit the changes after running the test
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    // Check if a new-created role is saved in the DB
    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role savedRole = roleRepository.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateOtherRoles() {
        Role roleSalesman = new Role("Salesman", "manage product price, " + "customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories, brands, " + "products, articles and menus");
        Role roleShipper = new Role("Shipper", "view products, view orders " + "and update order status");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews");

        roleRepository.saveAll(List.of(roleSalesman, roleEditor, roleShipper, roleAssistant));
    }
}
