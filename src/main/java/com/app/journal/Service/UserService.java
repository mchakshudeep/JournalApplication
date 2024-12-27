package com.app.journal.Service;

import com.app.journal.Entity.User;
import com.app.journal.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }

    public void createAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);
    }



    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    public User findByUsername(String username){
        return  userRepository.findByUsername(username);
    }

    public void update(String username, User updatedUser){
        User userInDb = userRepository.findByUsername(username);
        userInDb.setUsername(updatedUser.getUsername()!=null && !updatedUser.getUsername().isEmpty() ? updatedUser.getUsername() : userInDb.getUsername());
        userInDb.setPassword(passwordEncoder.encode(updatedUser.getPassword()!=null && !updatedUser.getPassword().isEmpty() ? updatedUser.getPassword(): userInDb.getPassword()));
        userRepository.save(userInDb);
    }

}
