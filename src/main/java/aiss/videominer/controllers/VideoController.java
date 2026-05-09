package aiss.videominer.controllers;

import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.models.Caption;
import aiss.videominer.models.Comment;
import aiss.videominer.models.Video;
import aiss.videominer.repository.CaptionRepository;
import aiss.videominer.repository.CommentRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("videominer/videos")
public class VideoController {
    @Autowired
    VideoRepository repository;

        @Autowired
        CaptionRepository captionRepository;

        @Autowired
        CommentRepository commentRepository;

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
            @PathVariable String id) throws VideoNotFoundException {

        Optional<Video> video = repository.findById(id);
        if (!video.isPresent()){
            throw new VideoNotFoundException();
        }
        return video.get();
    }


    @Operation(
            summary = "Get captions for a video",
            description = "Gets the list of captions for a given video identifier.",
            tags = { "VideoMiner Videos" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Caption list retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Caption.class)
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
    @GetMapping("/{id}/captions")
    public List<Caption> getCaptionsByVideoId(
            @Parameter(
                    description = "Identifier of the video to retrieve captions for",
                    example = "video123"
            )
            @PathVariable String id) throws VideoNotFoundException {
        if (!repository.existsById(id)) {
            throw new VideoNotFoundException();
        }
        return captionRepository.findByVideoId(id);
    }


    @Operation(
            summary = "Get comments for a video",
            description = "Gets the list of comments for a given video identifier.",
            tags = { "VideoMiner Videos" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment list retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
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
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByVideoId(
            @Parameter(
                    description = "Identifier of the video to retrieve comments for",
                    example = "video123"
            )
            @PathVariable String id) throws VideoNotFoundException {
        if (!repository.existsById(id)) {
            throw new VideoNotFoundException();
        }
        return commentRepository.findByVideoId(id);
    }
}