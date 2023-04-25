package com.chupachats.controllers;

import com.chupachats.exception.UserNullFoundException;
import com.chupachats.models.User;
import com.chupachats.services.UserService;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity all() {
        try{
            return ResponseEntity.ok(userService.all());
        }catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(userService.findUserById(id));
        }catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id){
        try{
            userService.deleteUserById(id);
            return ResponseEntity.ok("Пользователь с ID:" + id + " удален!");
        }catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @PostMapping("/ban/{id}")
    public ResponseEntity banUserById(@PathVariable Long id){
        try{
            userService.banUserById(id);
            return ResponseEntity.ok("Пользователь с ID:" + id + " забанен!");
        }catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @PostMapping("/addImage/{id}")
    public ResponseEntity addImageById(@RequestBody MultipartFile file, @PathVariable Long id){
        try{
            userService.addImage(file, id);
            return ResponseEntity.ok("файл добавлен");
        }catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity getImageUserId(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(userService.getImageId(id));
        }catch (UserNullFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
}
