package aiss.videominer.controllers;

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
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
@RequestMapping("/captions")
public class CaptionController {
    @Autowired
    CaptionRepository repository;

    @Operation(
            summary = "Get all captions",
            description = "Gets the full list of captions stored in VideoMiner.",
            tags = { "VideoMiner Captions" }
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
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping
    public List<Caption> getAllCaptions(){
        return repository.findAll();
    }

    @Operation(
            summary = "Get a caption by ID",
            description = "Gets a caption stored in VideoMiner by its identifier.",
            tags = { "VideoMiner Captions" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Caption retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Caption.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Caption not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Caption getCaptionById(
            @Parameter(
                    description = "Identifier of the caption to retrieve",
                    example = "caption123"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}