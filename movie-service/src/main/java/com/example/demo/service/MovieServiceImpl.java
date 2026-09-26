package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Movie;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{
	private final MovieRepository movieRepository;

    MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public Optional<Movie> updateMovie(Long id, Movie updated) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(updated.getTitle());
            movie.setGenre(updated.getGenre());
            movie.setReleaseYear(updated.getReleaseYear());
            movie.setDirector(updated.getDirector());
            return movieRepository.save(movie);
        });
    }

    @Override
    public boolean deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            return false;
        }
        movieRepository.deleteById(id);
        return true;
    }
}
