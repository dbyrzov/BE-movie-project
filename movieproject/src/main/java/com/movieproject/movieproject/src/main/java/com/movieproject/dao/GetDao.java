package com.movieproject.dao;

import com.movieproject.bean.Comment;
import com.movieproject.bean.Movie;
import com.movieproject.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class GetDao extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    private final NamedParameterJdbcOperations namedTemplate;

    public GetDao(NamedParameterJdbcOperations namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public List<User> getUserRole(String email, String pass){
        //@formatter:off
        String sql = "SELECT r.role, u.id, u.firstname    " +
                     "  FROM role r, user u, user_role ur " +
                     " WHERE 1 = 1                        " +
                     "   AND r.role_id = ur.role_id       " +
                     "   AND u.id = ur.user_id            " +
                     "   AND u.password = :pass           " +
                     "   AND u.email = :email             ";
        //@formatter:on

        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("pass", pass);

        return namedTemplate.query(sql, sp, (r, i)-> {
            User u = new User();
            u.setId(r.getInt("id"));
            u.setRole(r.getString("role"));
            u.setName(r.getString("firstname"));
            return u;
        });
    }

    public List<Movie> getAllMovies() {
        //@formatter:off
        String sql = "SELECT category          " +
                     "     , director          " +
                     "     , music             " +
                     "     , title             " +
                     "     , date              " +
                     "     , studio            " +
                     "     , description       " +
                     "     , rating            " +
                     "     , poster            " +
                     "     , trailer           " +
                     "     , countryfilmed     " +
                     "     , imdbrating        " +
                     "     , language          " +
                     "     , countryOfShooting " +
                     "     , duration          " +
                     "  FROM movies            " +
                      "ORDER BY date DESC";
        //@formatter:on

        return namedTemplate.query(sql, new MapSqlParameterSource(), (r, i) -> {
            Movie m = new Movie();
            m.setCategory(r.getString("category"));
            m.setDirector(r.getString("director"));
            m.setMusic(r.getString("music"));
            m.setTitle(r.getString("title"));
            m.setDateOfCreation(r.getString("date"));
            m.setStudio(r.getString("studio"));
            m.setDescription(r.getString("description"));
            m.setRating(r.getString("rating"));
            m.setPoster(r.getString("poster"));
            m.setTrailer(r.getString("trailer"));
            m.setCountry(r.getString("countryfilmed"));
            m.setImdbRating(r.getString("imdbrating"));
            m.setLanguage(r.getString("language"));
            m.setLanguage(r.getString("countryOfShooting"));
            m.setLanguage(r.getString("duration"));
            return m;
        });
    }

    public List<String> getMovieActors(String title) {
        //@formatter:off
        String sql = "SELECT ma.actor                  " +
                     "  FROM movie_actors ma, movies m " +
                     " WHERE 1 = 1                     " +
                     "   AND m.filmid = ma.filmid      " +
                     "   AND m.title = :title          ";
        //@formatter:on

        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("title", title);
        return namedTemplate.query(sql, sp, (r, i) -> r.getString("actor"));
    }

    public List<String> getMovieMainActors(String title) {
        //@formatter:off
        String sql = "SELECT ma.actor                      " +
                     "  FROM movie_mainactors ma, movies m " +
                     " WHERE 1 = 1                         " +
                     "   AND m.filmid = ma.filmid          " +
                     "   AND m.title = :title              ";
        //@formatter:on

        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("title", title);
        return namedTemplate.query(sql, sp, (r, i) -> r.getString("actor"));
    }

    public List<String> getMovieAwards(String title) {
        //@formatter:off
        String sql = "SELECT ma.award                  " +
                     "  FROM movie_awards ma, movies m " +
                     " WHERE 1 = 1                     " +
                     "   AND m.filmid = ma.filmid      " +
                     "   AND m.title = :title          ";
        //@formatter:on
        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("title", title);
        return namedTemplate.query(sql, sp, (r, i) -> r.getString("award"));
    }

    public List<String> getUserWishList(Integer userId) {
        //@formatter:off
        String sql = "SELECT m.title                   " +
                     "  FROM movies m, wished_movies w " +
                     " WHERE 1 = 1                     " +
                     "   AND m.filmid = w.movie_id     " +
                     "   AND user_id = :userId         ";
        //@formatter:on
        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedTemplate.query(sql, sp, (r, i) -> r.getString("title"));
    }

    public List<String> getUserWatchedList(Integer userId) {
        //@formatter:off
        String sql = "SELECT m.title                    " +
                     "  FROM movies m, watched_movies w " +
                     " WHERE 1 = 1                      " +
                     "   AND m.filmid = w.movie_id      " +
                     "   AND user_id = :userId          ";
        //@formatter:on
        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedTemplate.query(sql, sp, (r, i) -> r.getString("title"));
    }

    public List<Comment> getMovieComments(String title) {
        //@formatter:off
        String sql = "SELECT u.firstname, mc.comment, mc.date " +
                "  FROM movie_comments mc, movies m, user u " +
                " WHERE 1 = 1                       " +
                "   AND m.filmid = mc.filmid        " +
                "   AND mc.user_id = u.id        " +
                "   AND m.title = :title            ";
        //@formatter:on
        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("title", title);
        return namedTemplate.query(sql, sp, (r, i) -> {
            Comment c = new Comment();
            c.setName(r.getString("firstname"));
            c.setComment(r.getString("comment"));
            c.setDate(r.getString("date"));
            return c;
        });
    }



    public List<String> whoWatchedIt(String title) {
        //@formatter:off
        String sql = "SELECT u.firstname                        " +
                     "  FROM user u, movies m, watched_movies w " +
                     " WHERE 1 = 1                              " +
                     "   AND u.id = w.user_id                   " +
                     "   AND m.filmid = w.movie_id              " +
                     "   AND m.title = :title                   ";
        //@formatter:on
        final SqlParameterSource sp = new MapSqlParameterSource()
                .addValue("title", title);
        return namedTemplate.query(sql, sp, (r, i) -> r.getString("firstname"));
    }


}
