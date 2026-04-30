package aiss.videominer.controllers;


import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/peertube/videominer")
public class PeerTubeChannelController {

    @Autowired
    ChannelRepository repository;


    @Autowired
    RestTemplate restTemplate;


    @Operation(
            summary = "Get a PeerTube channel",
            description = "Gets a PeerTube channel by its identifier, optionally including a limited number of videos and comments per video. By default the limits are 10 videos and 10 comments.",
            tags = { "PeerTube Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel retrieved successfully from PeerTube",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel not found in PeerTube",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal error while retrieving the PeerTube channel",
                    content = @Content
            )
    })
    @GetMapping("/{channelId}")
    public Channel getChannelFromPeerTube(
            @Parameter(
                    description = "Identifier of the PeerTube channel to retrieve",
                    example = "poney@peertube2.cpy.re"
            )
            @PathVariable String channelId,

            @Parameter(
                    description = "Maximum number of videos to include in the channel",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxVideos,

            @Parameter(
                    description = "Maximum number of comments to include per video",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxComments){
        return restTemplate.getForObject("http://localhost:8081/peertube/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
    }

    @Operation(
            summary = "Get and store a PeerTube channel",
            description = "Gets a PeerTube channel, transforms it to the VideoMiner format, and stores it in the VideoMiner service database.",
            tags = { "PeerTube Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Channel retrieved and stored successfully in VideoMiner",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel not found in PeerTube",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal error while retrieving or storing the channel",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{channelId}")
    public Channel postChannelFromPeerTube(
            @Parameter(
                    description = "Identifier of the PeerTube channel to retrieve and store",
                    example = "poney@peertube2.cpy.re"
            )
            @PathVariable String channelId,

            @Parameter(
                    description = "Maximum number of videos to include in the channel",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxVideos,

            @Parameter(
                    description = "Maximum number of comments to include per video",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxComments){
        Channel channel =  restTemplate.getForObject("http://localhost:8081/peertube/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
        return repository.save(channel);
    }

}
