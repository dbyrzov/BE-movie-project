package com.movieproject.services;

import com.movieproject.bean.BodyHTTP;
import com.movieproject.bean.Movie;
import com.movieproject.bean.User;
import com.movieproject.dao.PostDao;
import com.movieproject.dao.GetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	SecurityService securityService;

	private final GetDao getDao;
	private final PostDao postDao;

	public UserService(GetDao getDao, PostDao postDao) {
		this.getDao = getDao;
		this.postDao = postDao;
	}

	public ResponseEntity<String> login(String email, String pass) {
		System.out.println(email);
		System.out.println(pass);

		BodyHTTP body = new BodyHTTP();
		List<User> usList;
		usList = getDao.getUserRole(email, pass);
		User user = usList.get(0);

		if (!"".equals(user.getRole())) {
			user.setToken(securityService.generateToken());
			SecurityService.users.add(user);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Access-Control-Allow-Origin", "*");

			body.setPair("x-auth-token", user.getToken());
			body.setPair("role", user.getRole());

			return ResponseEntity.status(HttpStatus.OK)
					.headers(responseHeaders)
					.body(body.toString());
		} else {

			// TODO: SHOULD CREATE ERROR HEADER
			body.setPair("auth", "NONE");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(body.toString());
		}
	}

	public List<Movie> getAllMovies() {
		List<Movie> movieList = getDao.getAllMovies();
		List<String> actors;
		List<String> mainActors;
		List<String> awards;

		for (Movie movie: movieList) {
			actors = getDao.getMovieActors(movie.title);
			movie.setActors( actors);

			mainActors = getDao.getMovieMainActors(movie.title);
			movie.setMainActors(mainActors);

			awards = getDao.getMovieAwards(movie.title);
			movie.setAwards(awards);
		}
		return movieList;
	}

	public List<String> getUserWishList(String token) {
		return getDao.getUserWishList(securityService.getUserId(token));
	}

	public List<String> getUserWatchedList(String token) { return getDao.getUserWatchedList(securityService.getUserId(token)); }

	public List<String> whoWatchedIt(String title) { return getDao.whoWatchedIt(title); }

	public boolean addMovieToWishList(String token, String title) {
		postDao.addMovieToWishList(securityService.getUserId(token), title);
		return true;
	}

}