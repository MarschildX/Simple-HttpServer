package net.xufang.util;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Respond {
    private OutputStream out;
    private Socket socket;
    private Request request;
    private FileUtil fileutil;
    private LoginUtil loginutil;
    private RegisterUtil registerutil;


    public Respond(Socket socket,Request request){
        this.socket=socket;
        this.request=request;
        try {
            out = socket.getOutputStream();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        respond();
    }

    public void respond(){     //响应请求，做出正确回应
        String path=request.getPath();
        fileutil=new FileUtil(path,out);
        loginutil=new LoginUtil(request,out);
        registerutil=new RegisterUtil(request,out);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print(df.format(new Date())+"  from:"+socket.getInetAddress().getHostAddress()+"   Request:"+request.getMethod()+"    result:");
        if(!fileutil.isFileExist()){
            this.doNotFound();
            System.out.println("HTTP/1.1 404 Not Found");
        }
        else{
            if(request.getMethod().equals("GET")){
                this.doOK();
                System.out.println("HTTP/1.1 200 OK--for GET request");
            }
            else if(request.getMethod().equals("HEAD")){
                this.doHead();
                System.out.println("HTTP/1.1 200 OK--for Head request");
            }
            else if(request.getMethod().equals("POST")){    //目前本服务器默认post操作即为登陆操作
                if(request.isPut()){
                    this.doRegister();
                    System.out.println("HTTP/1.1 200 OK--for register by POST request");
                }
                else {
                    this.doLogin();
                    System.out.println("HTTP/1.1 200 OK--for Login request");
                }
            }
            else if(request.getMethod().equals("PUT")){   //目前本服务器默认put操作为注册操作
                doRegister();
                System.out.println("HTTP/1.1 200 OK--for register by PUT request");
            }
            else{
                System.out.println("HTTP/1.1 200 OK--just OK");
            }
        }
    }

    public void doRedirect(){

    }

    public void doRegister(){   //当请求是注册账号时调用该方法
        try{
            registerutil.register();
            String header=combineHeader();
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write(header.getBytes());
            out.flush();
            registerutil.send();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void doNotFound(){    //没找到资源时调用该方法
        try {
            String header=combineHeader();
            out.write("HTTP/1.1 404 Not Found\r\n".getBytes());
            out.write(header.getBytes());
            out.flush();
            fileutil.sendNotFound();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doOK(){      //响应GET请求没问题时调用该方法
        try{
            String header=this.combineHeader();
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write(header.getBytes());
            out.flush();
            fileutil.parserAndSend();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void doHead(){     //响应HEAD请求时调用
        try{
            String header=this.combineHeader();
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write(header.getBytes());
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void doLogin(){      //响应登陆请求时调用
        try{
            String header=this.combineHeader();
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write(header.getBytes());
            out.flush();
            loginutil.send();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String combineHeader(){        //该方法很重要，是对响应头进行组装
        StringBuffer bf=new StringBuffer();

        String connection=request.getConnection();
        if(connection!=null){
            bf.append("Connection: "+connection);
            bf.append("\r\n");
        }

        SimpleDateFormat sf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.US);
        String time=sf.format(new Date());
        time=time+" GMT";
        bf.append("Date: "+time);
        bf.append("\r\n");

        bf.append("Catch-Control: private");
        bf.append("\r\n");

        if(fileutil.isFileExist()) {
            bf.append("Content-Type: " + fileutil.returnFileType());
            bf.append("\r\n");
        }
        else{
            bf.append("Content-Type: "+"text/html");
            bf.append("\r\n");
        }

        bf.append("Accept-Encoding: gzip");
        bf.append("\r\n");

        if(request.getMethod().equals("POST")){
            if(request.isPut()){
                bf.append("Content-Length: "+Long.toString(registerutil.getContentLength()));
                bf.append("\r\n");
            }
            else {
                bf.append("Content-Length: " + Long.toString(loginutil.getContentLength()));
                bf.append("\r\n");
            }
        }
        else if(request.getMethod().equals("GET")) {
            bf.append("Content-Length: " + Long.toString(fileutil.getFileLength()));
            bf.append("\r\n");
        }

        if(request.getMethod().equals("HEAD")){
            bf.append("Last-Modified: "+fileutil.getLastModify());
            bf.append("\r\n");
        }

        bf.append("\r\n");

        String data=new String(bf);
        return data;
    }
}
