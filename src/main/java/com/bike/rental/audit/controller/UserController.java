package com.bike.rental.audit.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bike.rental.audit.model.User;
import com.bike.rental.audit.service.UserService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }





    @PostMapping
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.created(
                        linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).toUri())
                .body(EntityModel.of(createdUser));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(EntityModel.of(updatedUser));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public CollectionModel<EntityModel<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<EntityModel<User>> userModels = users.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()))
                .toList();

        return CollectionModel.of(userModels,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }}

