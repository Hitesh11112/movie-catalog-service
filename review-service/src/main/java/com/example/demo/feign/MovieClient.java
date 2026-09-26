package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.MovieDto;

@FeignClient(name = "movie-service", url = "${movie-service.url}")
public interface MovieClient {
	@GetMapping("/api/movies/{id}")
    MovieDto getMovieById(@PathVariable("id") Long id);
}
