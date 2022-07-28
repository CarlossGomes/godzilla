package com.application.godzilla.controller;

import com.application.godzilla.model.User;
import com.application.godzilla.model.dto.UserDto;
import com.application.godzilla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@PathVariable(value = "id") Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
