package cn.itcast.web;
/**
 * 2021年4月15日
 * 下载文件案例
 * 重点：响应头的设置，包括响应头类型和响应头打开方式
 */


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;


@WebServlet("/downloadServlet")
public class downloadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("filename");
        // 寻找文件服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/img/" + filename);
        // 字节流关联
        FileInputStream fileInputStream = new FileInputStream(realPath);

        // 响应头设置
        // 响应头类型：Content-Type
        String mimeType = servletContext.getMimeType(filename);  // 获取文件类型
        resp.setHeader("content-type", mimeType);
        // 响应头打开方式：content-disposition
        resp.setHeader("content-disposition", "attachment;filename="+filename);  // 设置为附件模式

        // 输入流的数据写至输出流中
        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] buff = new byte[1024 * 8];
        int len = 0;
        while ((len = fileInputStream.read(buff)) != -1){  // 此处-1表明已读到最后一个字节
            outputStream.write(buff, 0, len);
        }

        // 关闭输入流
        fileInputStream.close();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}
