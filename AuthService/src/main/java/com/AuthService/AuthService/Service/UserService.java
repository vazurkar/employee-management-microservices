package com.AuthService.AuthService.Service;

import com.AuthService.AuthService.DTO.UserDto;
import com.AuthService.AuthService.Model.User;
import com.AuthService.AuthService.Repository.UserRepository;
import com.AuthService.AuthService.Response.JwtTokenResponse;
import com.AuthService.AuthService.Util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    public UserDto saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return new UserDto(user.getId(), savedUser.getUsername(),savedUser.getEmail(), savedUser.getRole());

    }

    public JwtTokenResponse generateToken(String username){
        String token = jwtUtil.generateToken(username);
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
        jwtTokenResponse.setJwtToken(token);
        jwtTokenResponse.setType("Bearer");
        jwtTokenResponse.setValidUntil(jwtUtil.extractExpiration(token).toString());
        return jwtTokenResponse;
    }
}
