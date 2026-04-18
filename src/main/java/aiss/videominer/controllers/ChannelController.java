package aiss.videominer.controllers;


import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/channels")
public class ChannelController {
    @Autowired
    ChannelRepository repository;


    @GetMapping
    public List<Channel> getAllChannels() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Channel getChannelById(@PathVariable String id) {
        return repository.findById(id).get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel createChannel(@Valid @RequestBody Channel channel) {
        return repository.save(new Channel(channel.getId() ,channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateChannel(@PathVariable String id, @Valid @RequestBody Channel channel) {
        Optional<Channel> channelData = repository.findById(id);

        Channel _channel = channelData.get();
        _channel.setName(channel.getName());
        _channel.setDescription(channel.getDescription());
        _channel.setCreatedTime(channel.getCreatedTime());
        _channel.setVideos(channel.getVideos());
        repository.save(_channel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable String id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
