package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
	private Long id;
    private String title;
    private String genre;
    private int releaseYear;
    private String director;
}
