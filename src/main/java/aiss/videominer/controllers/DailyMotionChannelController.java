package aiss.videominer.controllers;

import aiss.videominer.models.Channel;
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
@RequestMapping("/dailymotion/videominer")
public class DailyMotionChannelController {

    @Autowired
    ChannelRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @Operation(
            summary = "Get a Dailymotion channel",
            description = "Gets a Dailymotion channel by its identifier, optionally including a limited number of videos and result pages. By default the limits are 10 videos and 2 pages.",
            tags = { "Dailymotion Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel retrieved successfully from Dailymotion",
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
                    description = "Channel not found in Dailymotion",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal error while retrieving the Dailymotion channel",
                    content = @Content
            )
    })
    @GetMapping("/{channelId}")
    public Channel getChannelFromDailyMotion(
            @Parameter(
                    description = "Identifier of the Dailymotion channel to retrieve",
                    example = "x1abcde"
            )
            @PathVariable String channelId,

            @Parameter(
                    description = "Maximum number of videos to include in the channel",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxVideos,

            @Parameter(
                    description = "Maximum number of result pages to retrieve from Dailymotion",
                    example = "2"
            )
            @RequestParam(defaultValue = "2") int maxPages) {

        return restTemplate.getForObject(
                "http://localhost:8082/dailymotion/" + channelId
                        + "?maxVideos=" + maxVideos
                        + "&maxPages=" + maxPages,
                Channel.class
        );
    }

    @Operation(
            summary = "Get and store a Dailymotion channel",
            description = "Gets a Dailymotion channel, transforms it to the VideoMiner format, and stores it in the VideoMiner service database.",
            tags = { "Dailymotion Channels" }
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
                    description = "Channel not found in Dailymotion",
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
    public Channel postChannelFromDailyMotion(
            @Parameter(
                    description = "Identifier of the Dailymotion channel to retrieve and store",
                    example = "x1abcde"
            )
            @PathVariable String channelId,

            @Parameter(
                    description = "Maximum number of videos to include in the channel",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxVideos,

            @Parameter(
                    description = "Maximum number of result pages to retrieve from Dailymotion",
                    example = "2"
            )
            @RequestParam(defaultValue = "2") int maxPages) {

        Channel channel = restTemplate.getForObject(
                "http://localhost:8082/dailymotion/" + channelId
                        + "?maxVideos=" + maxVideos
                        + "&maxPages=" + maxPages,
                Channel.class
        );

        return repository.save(channel);
    }
}