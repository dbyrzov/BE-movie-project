package com.movieproject.services;

import com.movieproject.bean.BodyHTTP;
import com.movieproject.bean.Comment;
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

	public ResponseEntity<?> login(String email, String pass) {
		List<User> usList;
		usList = getDao.getUserRole(email, pass);
		User user = usList.get(0);

		user.setToken("Bearer " + securityService.generateToken());
		SecurityService.users.add(user);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		responseHeaders.set("Access-Control-Expose-Headers", "Authorization, Admin, Name, User_id");
		responseHeaders.set("Authorization", user.getToken());
		responseHeaders.set("Admin", user.getRole());
		responseHeaders.set("Name", user.getName());
		responseHeaders.set("User_id", user.getId() + "");

		return ResponseEntity.ok().headers(responseHeaders).build();
	}

	public List<Movie> getAllMovies() {
		List<Movie> movieList = getDao.getAllMovies();
		List<String> actors;
		List<String> mainActors;
		List<String> awards;
		List<Comment> comments;

		for (Movie movie: movieList) {
			actors = getDao.getMovieActors(movie.title);
			movie.setActors( actors);

			mainActors = getDao.getMovieMainActors(movie.title);
			movie.setMainActors(mainActors);

			awards = getDao.getMovieAwards(movie.title);
			movie.setAwards(awards);

			comments = getDao.getMovieComments(movie.title);
			movie.setComments(comments);
		}
		return movieList;
	}

	public List<String> getUserWishList(String token) {
		return getDao.getUserWishList(securityService.getUserId(token));
	}

	public List<String> getUserWatchedList(String token) { return getDao.getUserWatchedList(securityService.getUserId(token)); }

	public List<String> whoWatchedIt(String title) { return getDao.whoWatchedIt(title); }

	public void addMovieToWishList(String token, String title) {
		postDao.addMovieToWishList(securityService.getUserId(token), title);
	}

	public void saveMovieComment(String token, Comment comment) {
		postDao.saveMovieComment(securityService.getUserId(token), comment.getTitle(), comment.getComment(), comment.getDate());
	}

}