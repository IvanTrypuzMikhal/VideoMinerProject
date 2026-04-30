package aiss.videominer.controllers;

import aiss.videominer.model.User;
import aiss.videominer.repository.UserRepository;
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
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository repository;

    @Operation(
            summary = "Get all users",
            description = "Gets the full list of users stored in VideoMiner.",
            tags = { "VideoMiner Users" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User list retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
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
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @Operation(
            summary = "Get a user by ID",
            description = "Gets a user stored in VideoMiner by its identifier.",
            tags = { "VideoMiner Users" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public User getUserById(
            @Parameter(
                    description = "Identifier of the user to retrieve",
                    example = "1"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}