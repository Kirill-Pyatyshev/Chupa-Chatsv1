package com.chupachats.controllers;

import com.chupachats.dto.AuthUserDto;
import com.chupachats.dto.AuthUserResponceDto;
import com.chupachats.exception.UserNullFoundException;
import com.chupachats.models.User;
import com.chupachats.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
//@CrossOrigin("${jivys.hosting}")
public class AuthController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody User user) {
        try {
            return ResponseEntity.ok().body(userService.createUser(user));

        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("Invalid email or password");
        } catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @PostMapping ("/login")
    public ResponseEntity login(@RequestBody AuthUserDto authUserDto){
        try {
            return ResponseEntity.ok(userService.login(authUserDto));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
}
