package com.thuydieutran.admin.user;

import com.thuydieutran.kidshopcommon.entities.Role;
import com.thuydieutran.kidshopcommon.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);

        if (isUpdatingUser) {
            // In editing mode, we need to retrieve this infos from database;
            User existingUser = userRepository.findById(user.getId()).get();
            // Check if the password in the form is empty or not, if yes, means he wants to keep it
            //
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
        // In new user creating mode
            encodePassword(user);
        }
        // Where we create new user or modify an existing user, we persist his infos
        return userRepository.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByMail = userRepository.getUserByEmail(email);
        /*
        Because no user is found with the given email meaning that the email created will be unique in database
        Then we check for the case that the user is being edited
        */
        if (userByMail == null) return true;
        /*
        If (id == null) means that the form is in new mode (that means empty inputs, no existant infos showed)
        Otherwise, the form is in edit mode (that means the user's infos are going to be edited)
        */
        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            // By creating new user, if the userByMail is not null means there is already another user having this email
            // so the uniqueness of the email is false
            return false;
        } else {
            // In the edit mode, check if the id of the user found by email is different from the id of the user being edited, that means the email is not unique
            return Objects.equals(userByMail.getId(), id);
        }
    }

    public User get(Integer id) throws UserNotFoundException {
        // This method can throw a NoSuchElementException, so we need to use try catch statement to show error
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            // Need to create this custom exception by create class UserNotFoundException
            throw new UserNotFoundException("Could not find any user with ID" + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        // The reason that we need to call countById() because we don't want to call the get() method that returns a full user object with full details
        // For checking the existence of a user, we can use countById() method that just return a number
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnableStatus(Integer id, boolean enable) {
        userRepository.updateEnableStatus(id, enable);
    }
}
