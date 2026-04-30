package aiss.videominer.controllers;

import aiss.videominer.model.User;
import aiss.videominer.model.Video;
import aiss.videominer.repository.UserRepository;
import aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get all videos",
            description = "Gets the full list of videos stored in VideoMiner.",
            tags = { "VideoMiner Videos" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Video list retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Video.class)
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
    public List<Video> getAllVideos(){
        return repository.findAll();
    }

    @Operation(
            summary = "Get a video by ID",
            description = "Gets a video stored in VideoMiner by its identifier.",
            tags = { "VideoMiner Videos" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Video retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Video.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Video not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Video getVideoById(
            @Parameter(
                    description = "Identifier of the video to retrieve",
                    example = "video123"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}