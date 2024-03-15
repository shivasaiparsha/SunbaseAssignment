package com.example.SunBase.Controller;

import com.example.SunBase.Dtos.RequestDto.AddCustomerDto;
import com.example.SunBase.Dtos.RequestDto.AuthRequestDto;
import com.example.SunBase.Dtos.ResponseDto.JwtResponseDTO;
import com.example.SunBase.JwtFilter.JwtService;
import com.example.SunBase.Repository.CustomerRepository;
import com.example.SunBase.SecurityFilter.UserDetailsServiceImp;
import com.example.SunBase.Service.CustomerService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Builder
@CrossOrigin
public class CustomerRegistration {

    @Autowired
    private CustomerService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private CustomerRepository customerRepository;

    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @PostMapping("/AddCustomer")
    public ResponseEntity<String> userRegistration(@RequestBody AddCustomerDto adduserDto) throws  Exception {

        try {
            String message = userService.addUserToDb(adduserDto); // redirect user service class
            return new ResponseEntity<>(message, HttpStatus.OK);

        }
        catch (Exception e) { // other internal server error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/auth/login")
    public  ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){
         try {

             if(Authenticate(authRequestDTO)) {
                 String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());

                 return new ResponseEntity<>(accessToken, HttpStatus.OK);
             }
             else {
                 throw new UsernameNotFoundException("invalid user request..!!");
             }
         }
         catch (BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
         }catch (LockedException e) {
             return new ResponseEntity<>("User account is locked", HttpStatus.UNAUTHORIZED);
         } catch (DisabledException e) {
             return new ResponseEntity<>("User account is disabled", HttpStatus.UNAUTHORIZED);
         } catch (UsernameNotFoundException e){
             return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         }
         catch (Exception e) {
             return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
         }

    }

    @GetMapping("/welcome")
    public ResponseEntity<?> getUser()
    {
         String username="shivasai";
         String password="9908108643";
         AuthRequestDto authRequestDto=new AuthRequestDto(username, password);
         return new ResponseEntity<>(authRequestDto, HttpStatus.OK);
    }



    // Autnticate user
    public boolean Authenticate(AuthRequestDto authRequestDto) throws BadCredentialsException,UsernameNotFoundException
    {
        try {
            String username = authRequestDto.getUsername();
            String password = authRequestDto.getPassword();
            String encryptedPassword=passwordEncoder.encode(password);
            UserDetails user = customerRepository.findByUsername(authRequestDto.getUsername());// find the user in the database
            if(user==null) throw new  UsernameNotFoundException(username+" not found");// if user  not found
           if(passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword())) return true ; // if password matches return true
           else throw new BadCredentialsException("password incorrect");

        }
        catch (UsernameNotFoundException e)
        {
            throw new UsernameNotFoundException(e.getMessage());
        }
        catch (Exception e){

            throw new BadCredentialsException(e.getMessage());
        }

    }

}
