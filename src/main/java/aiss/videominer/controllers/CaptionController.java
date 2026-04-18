package aiss.videominer.controllers;

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/captions")
public class CaptionController {
    @Autowired
    CaptionRepository repository;

    @GetMapping
    public List<Caption> getAllCaptions(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Caption getCaptionById(@PathVariable String id){
        return repository.findById(id).get();
    }
}
