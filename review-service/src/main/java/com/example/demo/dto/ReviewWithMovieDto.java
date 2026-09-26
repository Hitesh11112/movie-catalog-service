package com.example.demo.dto;

import com.example.demo.entity.Review;

public class ReviewWithMovieDto {
	
	 private Review review;
	 private MovieDto movie;

	 public ReviewWithMovieDto(Review review, MovieDto movie) {
		 this.review = review;
		 this.movie = movie;
	 }

	 public Review getReview() { return review; }
	 public MovieDto getMovie() { return movie; }
}
