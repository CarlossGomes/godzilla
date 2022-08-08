package com.application.godzilla.controller;

import com.application.godzilla.model.User;
import com.application.godzilla.resources.AbstractCrudController;
import com.application.godzilla.resources.AbstractService;
import com.application.godzilla.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/users")
public class UserController extends AbstractCrudController<User> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AbstractService getService() {
        return this.userService;
    }


    @PostMapping(value = "/reset-password")
    public ResponseEntity<Void> resetPassword(HttpServletRequest request,
                                              @RequestParam("email") String userEmail) {
        userService.resetPassword(request, userEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody User user,
                                               @RequestParam("token") String token) {
        userService.resetPassword(user, token);
        return ResponseEntity.ok().build();
    }
}
