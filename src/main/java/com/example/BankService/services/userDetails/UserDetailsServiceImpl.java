package com.example.BankService.services.userDetails;

import com.example.BankService.models.Role;
import com.example.BankService.models.User;
import com.example.BankService.models.cards.DebitCard;
import com.example.BankService.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        userRepository.save(user);
        return true;
    }

    public boolean findUser(String username)
    {
        return userRepository.findByUsername(username) != null;
    }

    @PostConstruct
    public void initAdmin(){
        User user = new User();
        user.setUsername("admin");
        if (userRepository.findByUsername(user.getUsername()) == null){
            DebitCard debitCard = new DebitCard();
            user.setPassword("admin");
            user.setRole(Role.ADMIN);
            saveUser(user);
        }
    }
}
