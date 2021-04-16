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
 * Cookie快速入门
 * 此为接收cookie功能
 */
@WebServlet("/cookieRecvDemo")
public class cookieRecvDemo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // 接收cookie
        Cookie[] cookies = req.getCookies();
        // 获取数据，遍历cookie
        if ( cookies!=null ){
            for (Cookie c: cookies){
                String name = c.getName();
                String val = c.getValue();
                System.out.println(name+":"+val);
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.doPost(req, resp);
    }
}
