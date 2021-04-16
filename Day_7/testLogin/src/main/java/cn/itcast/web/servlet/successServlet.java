package cn.itcast.web.servlet;

import cn.itcast.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/successServlet")
public class successServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        User user = (User) req.getAttribute("user");  // 获得前面存储的数据
        String name = user.getUsername();

        if (name != null){
            // 设置编码
            resp.setContentType("text/html;charset=utf-8");
            // 输出
            resp.getWriter().write("登录成功!" + name + " 欢迎您！");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.doGet(req, resp);
    }

}
