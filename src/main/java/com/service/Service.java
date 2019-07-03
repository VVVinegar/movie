package com.service;

import com.bean.*;
//import com.hwadee.dao.DaoTools;
//import com.hwadee.dto.JobCountDto;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.ibatis.jdbc.SQL;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class Service {
    //需要使用数据源来进行连接
    private static DataSource dataSource;
    protected static final QueryRunner run = new QueryRunner(getDataSource());

    //使用C3p0数据库连接池
    public static DataSource getDataSource() {
        if (dataSource == null)
            //创建一个数据c3p0连接池
            dataSource = new ComboPooledDataSource("c3p0.properties");
        return dataSource;
    }

    public User login(String username, String password) throws SQLException {
        String sql = "select * from users where username=? and password=?";
        User user = (User) run.query(sql, new BeanHandler<User>(User.class), username, password);
        return user;
    }

    public List<Movie> findMoviesByGenere(String genere, int page, int pageSize) throws SQLException {
        String sql = "select * from movie where genere like ? limit ?, ?";
        String param = "%" + genere + "%";
        List<Movie> movieList = (List<Movie>) run.query(sql, new BeanListHandler<Movie>(Movie.class), param, (page - 1) * pageSize, pageSize);
        return movieList;
    }

    public int[] calRate(int userid, int movieid) throws SQLException {
        int[] result = new int[2];
        String sql = "select * from ratings where userid=? and movieid=?";
        Rate rate = (Rate) run.query(sql, new BeanHandler<Rate>(Rate.class), userid, movieid);
        if (rate != null) {
            int fancy = rate.getFancy();
            if (fancy != 1) {
                fancy = 1;
            } else {
                fancy = 0;
            }
            sql = "update ratings set fancy = ? where userid = ? and movieid = ? ";
            Object[] params = {fancy, userid, movieid};
            result[0] = run.update(sql, params);
            result[1] = fancy;
            return result;
        } else {
            sql = "insert into ratings (movieid,userid,fancy) values (?,?,?)";
            Object[] params = {movieid, userid, 1};
            result[0] = run.update(sql, params);
            result[1] = 1;
            return result;
        }
    }

    public List<Rate2> findratesByUserid(int userid) throws SQLException {
        String sql = "select movie.name,movie.genere,ratings.rating from ratings,movie where userid = ? and ratings.movieid = movie.movieId and fancy=1";
        List<Rate2> rates = (List<Rate2>) run.query(sql, new BeanListHandler<Rate2>(Rate2.class), userid);
        return rates;
    }

    public Movie findMoviesByID(int movieID) throws SQLException {
        String sql = "select * from movie where movieId=?";
        Movie movie = (Movie) run.query(sql, new BeanHandler<Movie>(Movie.class), movieID);
        return movie;
    }

    // 评分
    public Integer score(int userID, int movieID, int score) throws SQLException {
        String sql = "select * from ratings where userid=? and movieid=?";
        Rate rate = (Rate) run.query(sql, new BeanHandler<Rate>(Rate.class), userID, movieID);

        if (rate != null) {
            sql = "update ratings set rating = ? where userid = ? and movieid = ? ";
            Object[] params = {score, userID, movieID};
            return run.update(sql, params);
        } else {
            sql = "insert into ratings (userid, movieid, rating) values (?,?,?)";
            Object[] params = {userID, movieID, score};
            return run.update(sql, params);
        }
    }

    // 获取点赞情况
    public Integer getLike(int userID, int movieID) throws SQLException {
        String sql = "select fancy from ratings where userid=? and movieid=?";
        Rate rate = (Rate) run.query(sql, new BeanHandler<Rate>(Rate.class), userID, movieID);

        if (rate != null) {
            return rate.getFancy();
        } else {
            return 0;
        }
    }

    // 获取推荐电影（从计算结果）
    public List<UserScore> getRecommendFromCalculate(int userID) throws SQLException {
        String sql = "select * from userscore where userid=?";
        List<UserScore> UserScoreList = (List<UserScore>) run.query(sql, new BeanListHandler<UserScore>(UserScore.class), userID);
        return UserScoreList;
    }

    // 获取推荐电影（点赞最多的类型推荐同类型）
    public List<Integer> getUserLikeMovie(int userID) throws SQLException {
        String sql = "select movieid from ratings where userid=? and fancy=1";
        List movieIDList = (List) run.query(sql, new ColumnListHandler("movieid"), userID);
        return movieIDList;
    }

    // top N
    public List<Integer> getTopNMoviesID() throws SQLException {
        String sql = "select movieid from ratings where fancy = 1 GROUP BY movieid ORDER BY sum(fancy) desc limit 10";
        List movieIDList = (List) run.query(sql, new ColumnListHandler("movieid"));
        return movieIDList;
    }
}
