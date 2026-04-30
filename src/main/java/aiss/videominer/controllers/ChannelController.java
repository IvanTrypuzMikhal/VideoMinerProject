package aiss.videominer.controllers;


import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(
            summary = "Get all channels",
            description = "Gets the full list of channels stored in VideoMiner.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel list retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping
    public List<Channel> getAllChannels() {
        return repository.findAll();
    }

    @Operation(
            summary = "Get a channel by ID",
            description = "Gets a channel stored in VideoMiner by its identifier.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Channel getChannelById(
            @Parameter(
                    description = "Identifier of the channel to retrieve",
                    example = "channel123"
            )
            @PathVariable String id) {
        return repository.findById(id).get();
    }

    @Operation(
            summary = "Create a channel",
            description = "Creates a new channel in VideoMiner from the request body payload.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Channel created successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel createChannel(@Valid @RequestBody Channel channel) {
        return repository.save(new Channel(channel.getId() ,channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
    }

    @Operation(
            summary = "Update a channel",
            description = "Updates an existing channel in VideoMiner by its identifier.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Channel updated successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateChannel(
            @Parameter(
                    description = "Identifier of the channel to update",
                    example = "channel123"
            )
            @PathVariable String id,
            @Valid @RequestBody Channel channel) {
        Optional<Channel> channelData = repository.findById(id);
        Channel _channel = channelData.get();
        _channel.setName(channel.getName());
        _channel.setDescription(channel.getDescription());
        _channel.setCreatedTime(channel.getCreatedTime());
        _channel.setVideos(channel.getVideos());
        repository.save(_channel);
    }

    @Operation(
            summary = "Delete a channel",
            description = "Deletes a channel stored in VideoMiner by its identifier.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Channel deleted successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteChannel(
            @Parameter(
                    description = "Identifier of the channel to delete",
                    example = "channel123"
            )
            @PathVariable String id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}