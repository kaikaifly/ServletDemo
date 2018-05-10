package com.kk;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servlet0 extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = -4187075130535308117L;
    private boolean isMultipart;
    private int maxFileSize = 1024 * 1024 * 10;
    private int maxMemSize = 100 * 1024;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
//        //设置响应内容类型
//        response.setContentType("text/html");
//
//        //设置逻辑实现
//        PrintWriter out = response.getWriter();
//        out.println("<h1>" + "Hello world" + "</h1>");


        //处理请求
        //读取要下载的文件
//        File f = new File("D:/picture.png");
//        if (f.exists()) {
//            FileInputStream fis = new FileInputStream(f);
//            String filename = URLEncoder.encode(f.getName(), "utf-8"); //解决中文文件名下载后乱码的问题
//            byte[] b = new byte[fis.available()];
//            fis.read(b);
//            response.setCharacterEncoding("utf-8");
////            response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
//            response.setContentLength((int) f.length());
//            response.setContentType("image/png");
//            //获取响应报文输出流对象
//            ServletOutputStream out = response.getOutputStream();
//            //输出
//            out.write(b);
//            out.flush();
//            out.close();
//        }


//        String name = request.getParameter("name");
//        String age = request.getParameter("age");
//
        PrintWriter out = response.getWriter();


        DiskFileItemFactory factory = new DiskFileItemFactory();


        // 文件大小的最大值将被存储在内存中
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        String path = getServletContext().getRealPath("/") + "/";
        factory.setRepository(new File(path));
        // System.out.println(path);
        // 创建一个新的文件上传处理程序
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 允许上传的文件大小的最大值
        upload.setSizeMax(maxFileSize);


//        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List items = null;
        try {
            items = upload.parseRequest(request);
            HashMap param = new HashMap();
            for (Object object : items) {
                FileItem fileItem = (FileItem) object;
                if (fileItem.isFormField()) {
                    param.put(fileItem.getFieldName(), fileItem.getString("utf-8"));//如果你页面编码是utf-8的
                } else {
                    String fieldName = fileItem.getFieldName();
                    String fileName = fileItem.getName();
                    String contentType = fileItem.getContentType();
                    boolean isInMemory = fileItem.isInMemory();
                    long sizeInBytes = fileItem.getSize();
                    switch (fieldName) {
                        case "image":
                            File file = new File(path + fileName + ".png");
                            fileItem.write(file);
                            break;
                        case "txt":
                            File file1 = new File(path + fileName + ".txt");
                            fileItem.write(file1);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("failed");
        }

        out.print("success");

    }
}
