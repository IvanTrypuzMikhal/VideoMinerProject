package aiss.videominer.controllers;

import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/dailymotion/videominer")
public class DailyMotionChannelController {

    @Autowired
    ChannelRepository repository;


    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/{channelId}")
    public Channel getChannelFromPeerTube(@PathVariable String channelId, @RequestParam(defaultValue = "10") int maxVideos, @RequestParam(defaultValue = "10") int maxComments){
        return restTemplate.getForObject("http://localhost:8082/dailymotion/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{channelId}")
    public Channel postChannelFromPeerTube(@PathVariable String channelId, @RequestParam(defaultValue = "10") int maxVideos, @RequestParam(defaultValue = "10") int maxComments){
        Channel channel =  restTemplate.getForObject("http://localhost:8082/dailymotion/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
        return repository.save(channel);
    }

}

