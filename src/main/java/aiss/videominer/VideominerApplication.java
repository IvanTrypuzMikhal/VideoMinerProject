package aiss.videominer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@OpenAPIDefinition(
		info = @Info(
				title = "VideoMiner API",
				version = "1.0",
				description = "API REST para extraer canales, vídeos, subtítulos y comentarios de PeerTube y DailyMotion, transformarlos y recopilarlos."
		)
)
@SpringBootApplication
public class VideominerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideominerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) { return builder.build(); }


}
