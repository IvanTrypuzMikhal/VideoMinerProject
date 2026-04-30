package aiss.videominer.controllers;


import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/channels")
public class ChannelController {
    @Autowired
    ChannelRepository repository;


    @Operation(
            summary = "Obtener todos los canales",
            description = "Obtiene la lista completa de canales almacenados en VideoMiner.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de canales obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
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
    public List<Channel> getAllChannels() {
        return repository.findAll();
    }

    @Operation(
            summary = "Obtener un canal por ID",
            description = "Obtiene un canal almacenado en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Canal obtenido correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canal no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public Channel getChannelById(
            @Parameter(
                    description = "Identificador del canal que se desea obtener",
                    example = "canal123"
            )
            @PathVariable String id) {
        return repository.findById(id).get();
    }

    @Operation(
            summary = "Crear un canal",
            description = "Crea un nuevo canal en VideoMiner a partir de la información enviada en el cuerpo de la petición.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Canal creado correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Channel.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel createChannel(@Valid @RequestBody Channel channel) {
        return repository.save(new Channel(channel.getId() ,channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
    }

    @Operation(
            summary = "Actualizar un canal",
            description = "Actualiza los datos de un canal existente en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Canal actualizado correctamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canal no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateChannel(
            @Parameter(
                    description = "Identificador del canal que se desea actualizar",
                    example = "canal123"
            )
            @PathVariable String id,
            @Valid @RequestBody Channel channel) {
        Optional<Channel> channelData = repository.findById(id);
        Channel _channel = channelData.get();
        _channel.setName(channel.getName());
        _channel.setDescription(channel.getDescription());
        _channel.setCreatedTime(channel.getCreatedTime());
        _channel.setVideos(channel.getVideos());
        repository.save(_channel);
    }

    @Operation(
            summary = "Eliminar un canal",
            description = "Elimina un canal almacenado en VideoMiner a partir de su identificador.",
            tags = { "VideoMiner Channels" }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Canal eliminado correctamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canal no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteChannel(
            @Parameter(
                    description = "Identificador del canal que se desea eliminar",
                    example = "canal123"
            )
            @PathVariable String id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}