package cn.itcast.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2021年4月16日
 * 案例：显示上一次访问时间
 * 发送cookie时间
 */
@WebServlet("/cookieAlive")
public class cookieAlive extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // 创建cookie对象，创建同时绑定数据
        Cookie cookie = new Cookie("msg", "hello");
        // 发送cookie
        resp.addCookie(cookie);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.doPost(req, resp);
    }
}
