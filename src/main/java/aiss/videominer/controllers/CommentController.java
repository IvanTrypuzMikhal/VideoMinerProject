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
            summary = "Obtener todos los comentarios",
            description = "Obtiene la lista completa de comentarios almacenados en VideoMiner.",
            tags = { "VideoMiner Comments" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de comentarios obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
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
    public List<Comment> getAllComments(){
        return repository.findAll();
    }

    @Operation(
            summary = "Obtener un comentario por ID",
            description = "Obtiene un comentario almacenado en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Comments" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentario obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentario no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Comment getCommentById(
            @Parameter(
                    description = "Identificador del comentario que se desea obtener",
                    example = "comment123"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}