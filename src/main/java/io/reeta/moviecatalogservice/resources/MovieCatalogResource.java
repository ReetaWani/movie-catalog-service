package io.reeta.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.reeta.moviecatalogservice.model.CatalogItem;
import io.reeta.moviecatalogservice.model.Movie;
import io.reeta.moviecatalogservice.model.UserRating;
import io.reeta.moviecatalogservice.service.MovieInfoService;
import io.reeta.moviecatalogservice.service.RaatingDataService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private MovieInfoService movieInfoService;
	@Autowired
	private RaatingDataService ratingDataService;
	
	// @Autowired
	//private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
    // Here we specify which method to invoke on failure the argument of methods should match with current method
	//@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		UserRating ratings = ratingDataService.getRatingsData(userId);
		return ratings.getUserRating().stream().map(rating -> {
			Movie movie = movieInfoService.getMovieInfo(userId, rating.getMovieId());
			return new CatalogItem(movie.getName(), "Test", rating.getRating());
		}).collect(Collectors.toList());
		
	}
	
	/*
	 * public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String
	 * userId){ return Arrays.asList(new CatalogItem("No Movie", "", 0)); }
	 */

}

/*Movie movie = webClientBuilder
.build()  // Build the webClient builder
.get()    // Specify the type of HTTP method
.uri("http://localhost:8081/movies/"+rating.getMovieId()) // provide the URI to invoke
.retrieve()   // Get the response
.bodyToMono(Movie.class)  // once the response it got convert it into given object, here mono is to wait until the response is retrieved
.block();*/  // This blocks the request unitl respnse is got which is making asynchronous to synchronous
