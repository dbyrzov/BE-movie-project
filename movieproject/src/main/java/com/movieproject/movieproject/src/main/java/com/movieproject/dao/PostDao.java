package com.movieproject.dao;

import com.movieproject.bean.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PostDao {

    @Autowired
    DataSource dataSource;

//    @PostConstruct
//    private void initialize(){ setDataSource(dataSource); }

    private final NamedParameterJdbcOperations namedTemplate;

    public PostDao(NamedParameterJdbcOperations namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public void saveMovie(Movie m) {
        // TODO: SAVE MOVIE
    }

    public void addMovieToWishList(Integer user_id, String title) {
        //@formatter:off
        final String sql = "INSERT INTO  wished_movies (user_id, movie_id)            "
                         + " SELECT :user_id, filmid FROM movies WHERE title = :title ";
        //@formatter:on

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("title", title);
        namedTemplate.update(sql, paramMap);
    }

    public void saveMovieComment(Integer user_id, String title, String comment, String date) {
        //@formatter:off
        final String sql = "INSERT INTO  movie_comments (user_id, filmid, comment, date)            "
                + " SELECT :user_id, filmid, :comment, :date FROM movies WHERE title = :title ";
        //@formatter:on
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("title", title)
                .addValue("comment", comment)
                .addValue("date", date);
        namedTemplate.update(sql, paramMap);
    }
}
