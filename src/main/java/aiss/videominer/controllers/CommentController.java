package aiss.videominer.controllers;

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepository repository;

    @Operation(
            summary = "Get all comments",
            description = "Gets the full list of comments stored in VideoMiner.",
            tags = { "VideoMiner Comments" }
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
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping
    public List<Comment> getAllComments(){
        return repository.findAll();
    }

    @Operation(
            summary = "Get a comment by ID",
            description = "Gets a comment stored in VideoMiner by its identifier.",
            tags = { "VideoMiner Comments" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Comment getCommentById(
            @Parameter(
                    description = "Identifier of the comment to retrieve",
                    example = "comment123"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}