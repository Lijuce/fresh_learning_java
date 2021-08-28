package cn.itcast.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.logging.SimpleFormatter;

/**
 * 2021年4月16日
 * 案例：显示上一次访问时间
 * 发送和接收cookie时间都在同一个代码文件中完成
 */
@WebServlet("/cookieAliveRecv")
public class cookieAliveRec extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // 设置响应体数据格式及编码格式
        resp.setContentType("text/html;charset=utf-8");

        boolean flag = false;
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length>0){
            for (Cookie c : cookies){
                String name = c.getName();
                if ("lastTime".equals(name)){

                    flag = true;
                    String value = c.getValue();  // 获取日期时间

                    // 重新为cookie设值，即改为当前最新日期
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");// 日期的格式设置
                    String str_date = simpleDateFormat.format(date);
                    c.setValue(str_date);  // 重新赋值
                    // 设置cookie存活时间
                    c.setMaxAge(60 * 60 * 24);
                    resp.addCookie(c);


                    resp.getWriter().write("欢迎回来，上一次访问时间为"+value);

                    // 既然已经知道了结果，那么不必再循环查找cookie
                    break;
                }
            }
        }

        if (cookies==null || cookies.length == 0 || flag == false){
            // 表明是首次访问
            // 首次为cookie设值，即设为当前最新日期
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年mm月dd日HH:MM:SS");// 日期的格式设置
            String str_date = simpleDateFormat.format(date);
            Cookie lastTimeCookie = new Cookie("lastTime", str_date);
            // 设置cookie存活时间
            lastTimeCookie.setMaxAge(60 * 60 * 24);
            // 发送cookie
            resp.addCookie(lastTimeCookie);
            resp.getWriter().write("欢迎首次访问");

        }
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.doPost(req, resp);
    }
}
