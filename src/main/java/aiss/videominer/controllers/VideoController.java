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
            summary = "Obtener todos los vídeos",
            description = "Obtiene la lista completa de vídeos almacenados en VideoMiner.",
            tags = { "VideoMiner Videos" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de vídeos obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Video.class)
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
    public List<Video> getAllVideos(){
        return repository.findAll();
    }

    @Operation(
            summary = "Obtener un vídeo por ID",
            description = "Obtiene un vídeo almacenado en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Videos" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vídeo obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Video.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vídeo no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Video getVideoById(
            @Parameter(
                    description = "Identificador del vídeo que se desea obtener",
                    example = "video123"
            )
            @PathVariable String id){
        return repository.findById(id).get();
    }
}