package com.movieproject;

import com.movieproject.bean.Comment;
import com.movieproject.bean.Movie;
import com.movieproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Controller {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam("email") @Valid String email, @RequestParam("password") String pass) {
		return userService.login(email, pass);
	}

	@GetMapping("/home/movies")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<Movie> getAllMovies(@RequestHeader("Authorization") String token) {
		return userService.getAllMovies();
	}

	@GetMapping("/home/movies/wishList")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<String> getUserWishList(@RequestHeader("Authorization") String token) {
		return userService.getUserWishList(token);
	}

	@GetMapping("/home/movies/watchedList")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<String> getUserWatchedList(@RequestHeader("Authorization") String token) {
		return userService.getUserWatchedList(token);
	}

	@GetMapping("/home/movies/movie/watch")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<String> whoWatchedIt(@RequestHeader("Authorization") String token, @RequestParam String title) {
		return userService.whoWatchedIt(title);
	}


	/*****************************************************************************
	 *
	 *                          UPDATE / INSERT in DB
	 *
	 ****************************************************************************/


	@PostMapping("/home/movies/wishList/add")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public void addMovieToWishList(@RequestHeader("Authorization") String token, @RequestBody() String title) {
		userService.addMovieToWishList(token, title);
	}

	@PostMapping("/home/movies/movie/comment/add")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public void saveMovieComment(@RequestHeader("Authorization") String token, @RequestBody() Comment comment) {
		userService.saveMovieComment(token, comment);
	}
}
