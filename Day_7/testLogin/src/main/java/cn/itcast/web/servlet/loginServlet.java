package cn.itcast.web.servlet;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/login")
public class loginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        // 设置编码
        req.setCharacterEncoding("utf-8");
//        // 获取请求参数
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        // 封装user对象
//        User loginUser = new User();
//        loginUser.setUsername(username);
//        loginUser.setPassword(password);

        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        // 此处利用BeanUtils工具类来封装数据
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用login方法进行验证
        UserDao dao = new UserDao();
        User loginResult = dao.login(user);

        // 判断loginResult登录结果
        if (loginResult == null){
//            System.out.println("登录失败");
            // 进行页面跳转-转发-显示登录失败的结果
            req.getRequestDispatcher("/failedServlet").forward(req, resp);

        }else {
//            System.out.println("登录成功");
            // 保存数据，用于后面登录页面的可视化展示
            req.setAttribute("user", loginResult);

            // 页面跳转-转发-显示登录成功的结果
            req.getRequestDispatcher("/successServlet").forward(req, resp);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.doGet(req, resp);
    }

}
