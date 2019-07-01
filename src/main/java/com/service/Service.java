package com.service;
import com.bean.Movie;
import com.bean.Rate;
import com.bean.Rate2;
import com.bean.User;
//import com.hwadee.dao.DaoTools;
//import com.hwadee.dto.JobCountDto;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
public class Service {
	//需要使用数据源来进行连接
    private static DataSource dataSource;
    protected static final QueryRunner run = new QueryRunner(getDataSource());

	private static final int PAGE_SIZE = 3;

    //使用C3p0数据库连接池
    public static DataSource getDataSource() {
        if (dataSource == null)
        	//创建一个数据c3p0连接池
            dataSource = new ComboPooledDataSource("c3p0.properties");
        return dataSource;
    }
    
    public User login(String username,String password) throws  SQLException{
    	String sql = "select * from users where username=? and password=?";
    	User user = (User) run.query(sql, new BeanHandler<User>(User.class),username,password);
        return user;
    }
    
    public List<Movie> findMoviesByGenere(String genere, int page) throws SQLException{
    	String sql = "select * from movie where genere like ? " ;
    	String param = "%"+ genere +"%";
    	List<Movie> movieList =(List<Movie>) run.query(sql, new BeanListHandler<Movie>(Movie.class),param);
    	return movieList;
    }
    public Integer calRate(int userid,int movieid) throws SQLException {
    	String sql = "select * from ratings where userid=? and movieid=?";
		Rate rate = (Rate) run.query(sql, new BeanHandler<Rate>(Rate.class),userid,movieid);
    	if(rate!=null) {
    		int fancy=rate.getFancy();
        	if(fancy!=1) {
        		fancy=1;
        	}else {
    			fancy=0;
    		}
        	sql = "update ratings set fancy = ? where userid = ? and movieid = ? ";
        	Object[] params = {fancy,userid,movieid};
        	return run.update(sql,params);
    	}else {
    		sql = "insert into ratings (movieid,userid,fancy) values (?,?,?)";
    		Object[] params = {movieid,userid,1};
    		return run.update(sql, params);
    	}
	}
    public List<Rate2> findratesByUserid(int userid) throws SQLException{
    	String sql = "select movie.name,movie.genere,ratings.rating from ratings,movie where userid = ? and ratings.movieid = movie.movieId and fancy=1";
    	List<Rate2> rates = (List<Rate2>) run.query(sql, new BeanListHandler<Rate2>(Rate2.class),userid);
    	return rates;
    }
}
