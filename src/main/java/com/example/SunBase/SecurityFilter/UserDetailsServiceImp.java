package com.example.SunBase.SecurityFilter;

import com.example.SunBase.Models.User;
import com.example.SunBase.Repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Slf4j
public class UserDetailsServiceImp implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private CustomerRepository userRepository;
//     once the user enter credentials this method will invoke and check weather the user presents or not
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      UserDetails user=new User().getUser();

       // write a logic here to get the details from the database to authenticate the user

        if(user==null) {
            log.error("user not found");
            throw new UsernameNotFoundException("user not found exception");
        }

        return user;
    }

}

