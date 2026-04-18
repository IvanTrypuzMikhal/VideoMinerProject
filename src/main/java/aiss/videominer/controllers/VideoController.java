package aiss.videominer.controllers;

import aiss.videominer.model.User;
import aiss.videominer.model.Video;
import aiss.videominer.repository.UserRepository;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    VideoRepository repository;

    @GetMapping
    public List<Video> getAllVideos(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable String id){
        return repository.findById(id).get();
    }
}
