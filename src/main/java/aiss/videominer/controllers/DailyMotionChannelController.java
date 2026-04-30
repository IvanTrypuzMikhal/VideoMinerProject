package aiss.videominer.controllers;

import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/dailymotion/videominer")
public class DailyMotionChannelController {

    @Autowired
    ChannelRepository repository;


    @Autowired
    RestTemplate restTemplate;


    @Operation(
            summary = "Obtener un canal de Dailymotion",
            description = "Obtiene un canal de Dailymotion por su identificador, opcionalmente incluyendo un número limitado de vídeos y comentarios por vídeo. De manera predeterminada los límites son 10 vídeos y 10 comentarios.",
            tags = { "Dailymotion Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Canal obtenido correctamente desde Dailymotion",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetros de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canal no encontrado en Dailymotion",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al obtener el canal de Dailymotion",
                    content = @Content
            )
    })
    @GetMapping("/{channelId}")
    public Channel getChannelFromPeerTube(
            @Parameter(
                    description = "Identificador del canal de Dailymotion que se desea obtener",
                    example = "x1abcde"
            )
            @PathVariable String channelId,

            @Parameter(
                    description = "Número máximo de vídeos que se incluirán en el canal",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxVideos,

            @Parameter(
                    description = "Número máximo de comentarios que se incluirán por cada vídeo",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxComments){
        return restTemplate.getForObject("http://localhost:8082/dailymotion/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
    }

    @Operation(
            summary = "Obtener y guardar un canal de Dailymotion",
            description = "Obtiene un canal de Dailymotion, lo transforma al formato de VideoMiner y lo almacena en la base de datos del servicio VideoMiner.",
            tags = { "Dailymotion Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Canal obtenido y guardado correctamente en VideoMiner",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetros de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canal no encontrado en Dailymotion",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al obtener o guardar el canal",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{channelId}")
    public Channel postChannelFromPeerTube(
            @Parameter(
                    description = "Identificador del canal de Dailymotion que se desea obtener y guardar",
                    example = "x1abcde"
            )
            @PathVariable String channelId,

            @Parameter(
                    description = "Número máximo de vídeos que se incluirán en el canal",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxVideos,

            @Parameter(
                    description = "Número máximo de comentarios que se incluirán por cada vídeo",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int maxComments){
        Channel channel =  restTemplate.getForObject("http://localhost:8082/dailymotion/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
        return repository.save(channel);
    }

}

