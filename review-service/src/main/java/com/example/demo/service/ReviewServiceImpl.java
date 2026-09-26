package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MovieDto;
import com.example.demo.dto.ReviewWithMovieDto;
import com.example.demo.entity.Review;
import com.example.demo.feign.MovieClient;
import com.example.demo.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewRepository reviewRepository;
    private final MovieClient movieClient;

    ReviewServiceImpl(ReviewRepository reviewRepository, MovieClient movieClient) {
        this.reviewRepository = reviewRepository;
        this.movieClient = movieClient;
    } // OpenFeign client -> calls movie-service

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<ReviewWithMovieDto> getReviewsForMovie(Long movieId) {
        MovieDto movie = movieClient.getMovieById(movieId);
        List<Review> reviews = reviewRepository.findByMovieId(movieId);

        return reviews.stream()
                .map(review -> new ReviewWithMovieDto(review, movie))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            return false;
        }
        reviewRepository.deleteById(id);
        return true;
    }
}
