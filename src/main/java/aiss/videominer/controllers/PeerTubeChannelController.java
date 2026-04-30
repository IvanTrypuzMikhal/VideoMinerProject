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
@RequestMapping("/peertube/videominer")
public class PeerTubeChannelController {

    @Autowired
    ChannelRepository repository;


    @Autowired
    RestTemplate restTemplate;


    @Operation(
            summary = "Obtener un canal de PeerTube",
            description = "Obtiene un canal de PeerTube por su identificador, opcionalmente incluyendo un número limitado de vídeos y comentarios por vídeo. De manera predeterminada los límites son 10 vídeos y 10 comentarios.",
            tags = { "PeerTube Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Canal obtenido correctamente desde PeerTube",
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
                    description = "Canal no encontrado en PeerTube",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al obtener el canal de PeerTube",
                    content = @Content
            )
    })
    @GetMapping("/{channelId}")
    public Channel getChannelFromPeerTube(
            @Parameter(
                    description = "Identificador del canal de PeerTube que se desea obtener",
                    example = "poney@peertube2.cpy.re"
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
        return restTemplate.getForObject("http://localhost:8081/peertube/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
    }

    @Operation(
            summary = "Obtener y guardar un canal de PeerTube",
            description = "Obtiene un canal de PeerTube, lo transforma al formato de VideoMiner y lo almacena en la base de datos del servicio VideoMiner.",
            tags = { "PeerTube Channels" }
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
                    description = "Canal no encontrado en PeerTube",
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
                    description = "Identificador del canal de PeerTube que se desea obtener y guardar",
                    example = "poney@peertube2.cpy.re"
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
        Channel channel =  restTemplate.getForObject("http://localhost:8081/peertube/"+channelId+ "?maxVideos=" + maxVideos + "&maxComments=" + maxComments, Channel.class);
        return repository.save(channel);
    }

}
