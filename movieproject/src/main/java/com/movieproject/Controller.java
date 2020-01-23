package com.movieproject;

import com.movieproject.bean.Movie;
import com.movieproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestHeader("email") String email, @RequestHeader("password") String pass) {
		return userService.login(email, pass);
	}

	@GetMapping("/home/movies")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<Movie> getAllMovies(@RequestHeader("x-auth-token") String token) {
		System.out.println("TOKENA: " + token);
		return userService.getAllMovies();
	}

	@GetMapping("/home/movies/wishList")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<String> getUserWishList(@RequestHeader("x-auth-token") String token) {
		return userService.getUserWishList(token);
	}

	@GetMapping("/home/movies/watchedList")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<String> getUserWatchedList(@RequestHeader("x-auth-token") String token) {
		return userService.getUserWatchedList(token);
	}

	@GetMapping("/home/movies/movie/watch")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public List<String> whoWatchedIt(@RequestHeader("x-auth-token") String token, @RequestParam String title) {
		return userService.whoWatchedIt(title);
	}


	/*****************************************************************************
	 *
	 *                          UPDATE / INSERT in DB
	 *
	 ****************************************************************************/


	@PostMapping("/home/movies/wishList/add")
	@PreAuthorize(("@securityService.hasAccess(#token)"))
	public ResponseEntity<?> addMovieToWishList(@RequestHeader("x-auth-token") String token, @RequestBody() String title) {
		System.out.println("TITLE: " + title);
		if( userService.addMovieToWishList(token, title)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
