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
            summary = "Obtener todos los usuarios",
            description = "Obtiene la lista completa de usuarios almacenados en VideoMiner.",
            tags = { "VideoMiner Users" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de usuarios obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @Operation(
            summary = "Obtener un usuario por ID",
            description = "Obtiene un usuario almacenado en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Users" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public User getUserById(
            @Parameter(
                    description = "Identificador del usuario que se desea obtener",
                    example = "1"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}