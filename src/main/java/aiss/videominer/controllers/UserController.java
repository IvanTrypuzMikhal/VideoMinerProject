package aiss.videominer.controllers;

import aiss.videominer.model.User;
import aiss.videominer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository repository;

    @GetMapping
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id){
        return repository.findById(id).get();
    }
}
