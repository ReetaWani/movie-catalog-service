package io.reeta.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.reeta.moviecatalogservice.model.Rating;
import io.reeta.moviecatalogservice.model.UserRating;

@Service
public class RaatingDataService {
	@Autowired
	RestTemplate restTemplate;
	
	//@HystrixCommand(fallbackMethod = "getFallbackRatingData")
	@Cacheable("ratingData")
	public UserRating getRatingsData(String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
	}

	/*
	 * public UserRating getFallbackRatingData(String userId) { UserRating
	 * userRating = new UserRating(); userRating.setUserRating(Arrays.asList(new
	 * Rating("", 0))); return userRating; }
	 */

}
