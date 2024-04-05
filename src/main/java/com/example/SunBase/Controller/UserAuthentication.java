package com.example.SunBase.Controller;

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
@RequestMapping
@Builder
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserAuthentication {

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


    @PostMapping ("/auth/login")
    public  ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){

        System.out.println(authRequestDTO.getUsername()+" "+authRequestDTO.getPassword());
         try {
             if(Authenticate(authRequestDTO)) {
                 String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
                 JwtResponseDTO responseDTO=new JwtResponseDTO(accessToken);
                 return new ResponseEntity<>(responseDTO, HttpStatus.OK);
             }
             else {
                 throw new UsernameNotFoundException("invalid user request..!!");
             }
         }
         catch (BadCredentialsException e) {
            return new ResponseEntity<>("user not found", HttpStatus.INTERNAL_SERVER_ERROR);
         }catch (LockedException e) {
             return new ResponseEntity<>("User account is locked", HttpStatus.NOT_FOUND);
         } catch (DisabledException e) {
             return new ResponseEntity<>("User account is disabled", HttpStatus.CONFLICT);
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
         String password="12345678";
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
            UserDetails user = new UserDetailsServiceImp().loadUserByUsername(username);

            if(user==null) throw new  UsernameNotFoundException(username+" not found");// if user  not found
            //passwordEncoder.matches(encryptedPassword, passwordEncoder.encode(user.getPassword()))
           if(user.getPassword().equals(password)) return true ; // if password matches return true
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
