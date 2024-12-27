package com.app.journal.Controller;


import com.app.journal.Entity.User;
import com.app.journal.Service.UserDetailsServiceImpl;
import com.app.journal.Service.UserService;
import com.app.journal.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> signUp(@RequestBody User user){
        try{
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody User user){
        try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Incorrect username or passowrd",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check")
    public String healthCheck(){
        return "OK";
    }
}
