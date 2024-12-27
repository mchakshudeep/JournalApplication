package com.app.journal.Controller;


import com.app.journal.Entity.User;
import com.app.journal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/getAll")
//    public ResponseEntity<List<User>> getAllEntries(){
//        List<User> user=userService.getAll();
//        if(user!=null && !user.isEmpty()){
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<User> getById(@PathVariable ObjectId id){
//        Optional<User> user =userService.getById(id);
//        if(user.isPresent()){
//            return new ResponseEntity<>(user.get(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userService.update(authentication.getName(),updatedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
