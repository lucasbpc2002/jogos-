package br.tads.eaj.gamesapp;

import br.tads.eaj.gamesapp.domain.Jogos;
import br.tads.eaj.gamesapp.repository.JogosInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class GamesAppApplication implements WebMvcConfigurer {
	@Autowired
	private JogosInterface repository;

	public static void main(String[] args) {
		SpringApplication.run(GamesAppApplication.class, args);
	}
	@PostConstruct
	public void initFilmes() {

		List<Jogos> jogos = Stream.of(
				new Jogos(1, "god of war", "Santa monica", "Sony", "2018", "playstation", "50.50", "img.png" )

		).collect(Collectors.toList());

		repository.saveAll(jogos);

		}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Register resource handler for images
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
		//.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		/*
		registry.addResourceHandler("/images/**").addResourceLocations("/images/")
		.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());*/
	}
}
