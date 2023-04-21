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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;


    @GetMapping("/home")
    public ResponseEntity home(){
        return ResponseEntity.badRequest().body("HOME PAGE!");
    }

    @GetMapping("/registration")
    public ResponseEntity registration(@RequestBody User user) {
        try {
            AuthUserResponceDto responce = userService.createUser(user);
            return ResponseEntity.ok().body("Пользователь с email'ом " + responce.getEmail() +
                    " успешно зарегистрирован!\n" + responce.getToken());

        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("Invalid email or password");
        } catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @GetMapping("/login")
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
