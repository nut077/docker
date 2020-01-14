package com.github.nut077.docker.controller;

import com.github.nut077.docker.config.JwtTokenUtil;
import com.github.nut077.docker.dto.UserDto;
import com.github.nut077.docker.entity.JwtRequest;
import com.github.nut077.docker.entity.JwtResponse;
import com.github.nut077.docker.entity.User;
import com.github.nut077.docker.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.github.nut077.docker.dto.response.SuccessResponse.builder;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class JwtAuthenicationController extends CommonController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService userDetailsService;

  @PostMapping("/authenticate")
  public ResponseEntity createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    String token = jwtTokenUtil.generateToken(userDetails);
    return ok(builder(new JwtResponse(token)).build());
  }

  @PostMapping("/register")
  public ResponseEntity saveUser(@RequestBody UserDto userDto) {
    User user = userDetailsService.save(userDto);
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    String token = jwtTokenUtil.generateToken(userDetails);
    return ok()
      .header("Authorization", "Bearer " + token)
      .body(builder(user).build());
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
