package aiss.videominer.controllers;


import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/channels")
public class ChannelController {
    @Autowired
    ChannelRepository repository;


    @GetMapping
    public List<Channel> getAllChannels() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel createChannel(@Valid @RequestBody Channel channel) {
        return repository.save(new Channel(channel.getId() ,channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
    }
}
