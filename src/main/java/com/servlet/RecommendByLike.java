package com.servlet;

import com.alibaba.fastjson.JSON;
import com.bean.Movie;
import com.bean.UserScore;
import com.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class RecommendByLike extends HttpServlet {
    public RecommendByLike() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset = UTF-8");
        request.setCharacterEncoding("UTF-8");

        String userID = request.getParameter("userID");

        if ("" == userID) {
            // todo 没登录先推荐userID=1的
            userID = "1";
        }

        int userIDInt = Integer.parseInt(userID);

        Service service = new Service();

        try {
            Map<String, Integer> map = new HashMap<String, Integer>();
            List<Integer> movieIDs = service.getUserLikeMovie(userIDInt);
            for (int i = 0; i < movieIDs.size(); i++) {
                int movieID = (int) movieIDs.get(i);
                Movie movie = service.findMoviesByID(movieID);
                String genere = movie.getGenere();
                String[] temp = genere.split("\\|"); // 分割字符串
                // 普通 for 循环
                for (int j = 0; j < temp.length; j++) {
                    if (null == map.get(temp[j])) {
                        map.put(temp[j], 1);
                    } else {
                        map.put(temp[j], map.get(temp[j]) + 1);
                    }
                }
            }

            String maxGenere = findGenere(map);

            List<Movie> movies = service.findMoviesByGenere(maxGenere, 1, 5);

            String jsonStr = JSON.toJSON(movies).toString();

            PrintWriter out = response.getWriter();  // 打开response的输入流
            out.print(jsonStr);  // 写入信息到response中
            out.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private String findGenere(Map<String, Integer> map) {
        int count = 0;
        String maxGenere = null;
        List list = new ArrayList();

        Iterator ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            count = Integer.parseInt(entry.getValue().toString());
            list.add(entry.getValue());
            Collections.sort(list);

            if (count == Integer.parseInt(list.get(list.size() - 1).toString())) {
                maxGenere = entry.getKey().toString();
            }
        }
        return maxGenere;
    }
}
