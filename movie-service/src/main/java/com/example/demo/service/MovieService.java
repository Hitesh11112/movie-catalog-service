package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import com.example.demo.entity.Movie;

public interface MovieService {
	public Movie addMovie(Movie movie);
    public List<Movie> getAllMovies();
    public Optional<Movie> getMovieById(Long id);
    public Optional<Movie> updateMovie(Long id, Movie updated);
    public boolean deleteMovie(Long id);
}
