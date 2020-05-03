package io.reeta.moviecatalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.reeta.moviecatalogservice.model.Movie;

@Service
public class MovieInfoService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackMovieInfo")
	public Movie getMovieInfo(String userId, String movieId) {
		return restTemplate.getForObject("http://movie-info-service/movies/"+movieId, Movie.class);
	}
	
	public Movie getFallbackMovieInfo(String userId, String movieId) {
		return new Movie(movieId, "No Data");
	}
	

}
