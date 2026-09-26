package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ReviewWithMovieDto;
import com.example.demo.entity.Review;

public interface ReviewService {
	Review addReview(Review review);
    List<Review> getAllReviews();
    List<ReviewWithMovieDto> getReviewsForMovie(Long movieId);
    boolean deleteReview(Long id);
}
