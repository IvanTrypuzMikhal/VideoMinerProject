package aiss.videominer.controllers;

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepository repository;

    @GetMapping
    public List<Comment> getAllComments(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable String id){
        return repository.findById(id).get();
    }
}
