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
            summary = "Obtener todos los subtítulos",
            description = "Obtiene la lista completa de subtítulos almacenados en VideoMiner.",
            tags = { "VideoMiner Captions" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de subtítulos obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Caption.class)
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
    public List<Caption> getAllCaptions(){
        return repository.findAll();
    }

    @Operation(
            summary = "Obtener un subtítulo por ID",
            description = "Obtiene un subtítulo almacenado en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Captions" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Subtítulo obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Caption.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Subtítulo no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Caption getCaptionById(
            @Parameter(
                    description = "Identificador del subtítulo que se desea obtener",
                    example = "caption123"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}