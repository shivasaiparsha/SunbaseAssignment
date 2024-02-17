package com.example.SunBase.Controller;

import com.example.SunBase.Dtos.AddCustomerDto;
import com.example.SunBase.Dtos.AuthRequestDto;
import com.example.SunBase.JwtFilter.JwtService;
import com.example.SunBase.Service.CustomerService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Builder
public class CustomerRegistration {

    @Autowired
    private CustomerService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/AddCustomer") // user registerration
    public ResponseEntity<String> userRegistration(@RequestBody AddCustomerDto adduserDto) throws  Exception {

        try {
            String message = userService.addUserToDb(adduserDto); // redirect user service class
            return new ResponseEntity<>(message, HttpStatus.OK);

        }
        catch (Exception e) { // other internal server error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/tokenGenarate")
    public  ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){
         System.out.println(authRequestDTO.getUsername()+" "+authRequestDTO.getPassword());
         try {
             // authenticationManager will authenticate the user and it will authentication object
             // if user not provide proper credentials it will throw 401 Unauthorized Exception
             Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
             if(authentication.isAuthenticated()){
                 return new ResponseEntity<>(jwtService.GenerateToken(authRequestDTO.getUsername()), HttpStatus.OK);
             } else {
                 throw new UsernameNotFoundException("invalid user request..!!");
             }
         }
         catch (BadCredentialsException e) {
        return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
         }catch (LockedException e) {
             return new ResponseEntity<>("User account is locked", HttpStatus.UNAUTHORIZED);
         } catch (DisabledException e) {
             return new ResponseEntity<>("User account is disabled", HttpStatus.UNAUTHORIZED);
         } catch (Exception e) {
             return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
         }

    }





}
