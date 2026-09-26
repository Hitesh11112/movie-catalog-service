package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	 private final MovieService movieService;

	MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	 @PostMapping
	 public Movie addMovie(@RequestBody Movie movie) {
		 return movieService.addMovie(movie);
	 }

	 @GetMapping
	 public List<Movie> getAllMovies() {
		 return movieService.getAllMovies();
	 }

	 // Review Service calls this via OpenFeign
	 @GetMapping("/{id}")
	 public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
		 return movieService.getMovieById(id)
				 .map(ResponseEntity::ok)
				 .orElse(ResponseEntity.notFound().build());
	 }

	 @PutMapping("/{id}")
	 public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updated) {
		 return movieService.updateMovie(id, updated)
				 .map(ResponseEntity::ok)
				 .orElse(ResponseEntity.notFound().build());
	 }

	 @DeleteMapping("/{id}")
	 public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
		 if (!movieService.deleteMovie(id)) {
			 return ResponseEntity.notFound().build();
		 }
		 return ResponseEntity.noContent().build();
	 }
}
