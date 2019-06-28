package net.xufang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Request {
    private InputStream in;
    private OutputStream out;
    private Socket socket;

    private String method=null;
    private String path=null;
    private String host=null;
    private String accept=null;
    private String upgrade=null;
    private String accept_language=null;
    private String accept_encoding=null;
    private String cache_control=null;
    private String connection=null;
    private String referer=null;
    private String cookie=null;
    private String user_agent=null;
    private String version=null;
    private String charset=null;
    private int contentlength=0;
    private String content=null;
    private String username=null;
    private String password=null;
    private boolean put=false;

    public Request(Socket socket){
        this.socket=socket;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            this.getRequest();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getRequest(){         //控制传送过来的数据的读取与解析
        String dataline=readLine(in,0);
        parser(dataline);
        while(!dataline.equals("\r\n")){
            dataline=readLine(in,0);
            parser(dataline);
        }
        if("POST".equalsIgnoreCase(method)){     //解析账户与密码
            content=readLine(in,contentlength);
            System.out.println(content);
            if(content.contains("_method=PUT")){  //这是利用POST请求来注册账号
                put=true;
                parserRegister();
            }
            else {
                parserLogin();
            }
        }
        else if("PUT".equalsIgnoreCase(method)){
            content=readLine(in,contentlength);
            System.out.println(content);
            parserRegister();
        }
    }

    public void parser(String data){    //根据读取的数据设置相应变量值
        if(data.startsWith("GET")){
            String temp;
            this.method="GET";
            int index=data.indexOf("HTTP");
            temp=data.substring(4,index-1);
            this.path=temp;
            //System.out.println(path);
            temp=data.substring(index);
            temp=temp.trim();
            this.version=temp;
        }
        else if(data.startsWith("POST")){
            String temp;
            this.method="POST";
            int index=data.indexOf("HTTP");
            temp=data.substring(5,index-1);
            this.path=temp;
            temp=data.substring(index);
            temp=temp.trim();
            this.version=temp;
        }
        else if(data.startsWith("HEAD")){
            String temp;
            this.method="HEAD";
            int index=data.indexOf("HTTP");
            temp=data.substring(5,index-1);
            this.path=temp;
            temp=data.substring(index);
            temp=temp.trim();
            this.version=temp;
        }
        else if(data.startsWith("PUT")){
            String temp;
            this.method="PUT";
            int index=data.indexOf("HTTP");
            temp=data.substring(4,index-1);
            this.path=temp;
            temp=data.substring(index);
            temp=temp.trim();
            this.version=temp;
        }
        else if(data.startsWith("Accept:")){
            String temp=data.substring("Accept:".length()+1);
            temp=temp.trim();
            this.accept=temp;
        }
        else if(data.startsWith("User-Agent:")){
            String temp=data.substring("User-Agent:".length()+1);
            temp=temp.trim();
            this.user_agent=temp;
        }
        else if(data.startsWith("Host:")){
            String temp=data.substring("Host:".length()+1);
            temp=temp.trim();
            this.host=temp;
        }
        else if(data.startsWith("Accept-Language:")){
            String temp=data.substring("Accept-Language:".length()+1);
            temp=temp.trim();
            this.accept_language=temp;
        }
        else if(data.startsWith("Accept-Charset:")){
            String temp=data.substring("Accept-Charset:".length()+1);
            temp=temp.trim();
            this.charset=temp;
        }
        else if(data.startsWith("Accept-Encoding:")){
            String temp=data.substring("Accept-Encoding:".length()+1);
            temp=temp.trim();
            this.accept_encoding=temp;
        }
        else if(data.startsWith("Connection:")){
            String temp=data.substring("Connection:".length()+1);
            temp=temp.trim();
            this.connection=temp;
        }
        else if(data.startsWith("Cache-Control:")){
            String temp=data.substring("Cache-Control:".length()+1);
            temp=temp.trim();
            this.cache_control=temp;
        }
        else if(data.startsWith("Referer:")){
            String temp=data.substring("Referer:".length()+1);
            temp=temp.trim();
            this.referer=temp;
        }
        else if(data.startsWith("Cookie:")){
            String temp=data.substring("Cookie:".length()+1);
            temp=temp.trim();
            this.cookie=temp;
        }
        else if(data.startsWith("Upgrade-Insecure-Requests:")){
            String temp=data.substring("Upgrade-Insecure-Requests:".length()+1);
            temp=temp.trim();
            this.upgrade=temp;
        }
        else if(data.startsWith("Content-Length:")){
            this.contentlength=Integer.parseInt(data.split(":")[1].trim());
        }
    }

    public void parserLogin(){     //解析登陆过程的数据
        int start,end;
        String cut;

        start=content.indexOf("username=");
        start+="username=".length();
        end=content.indexOf("&",start);
        cut=content.substring(start,end);
        this.username=cut;

        end=-1;
        start=content.indexOf("password=");
        start+="password=".length();
        end=content.indexOf("&",start);
        if(end==-1){
            cut=content.substring(start);
        }
        else{
            cut=content.substring(start,end);
        }
        this.password=cut;
    }

    public void parserRegister(){      //解析注册账号的数据
        int start,end;
        String cut;

        start=content.indexOf("username=");
        start+="username=".length();
        end=content.indexOf("&",start);
        cut=content.substring(start,end);
        this.username=cut;

        end=-1;
        start=content.indexOf("password=");
        start+="password=".length();
        end=content.indexOf("&",start);
        if(end==-1){
            cut=content.substring(start);
        }
        else{
            cut=content.substring(start,end);
        }
        this.password=cut;
    }

    public String readLine(InputStream in,int flag){      //这个方法很重要，用来读取数据
        ArrayList<Byte> datalist=new ArrayList<Byte>();
        byte data=0;
        int total=0;
        if(flag==0){
            do{
                try {
                    data=(byte)in.read();
                    total++;
                    datalist.add(Byte.valueOf(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while(data!=10);
        }
        else{
            do{
                try {
                    data=(byte)in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                total++;
                datalist.add(Byte.valueOf(data));
            }while(total<flag);
        }
        byte[] tempbytedata=new byte[datalist.size()];
        for(int i=0;i<datalist.size();i++){
            tempbytedata[i]=datalist.get(i).byteValue();
        }
        String dataline=new String(tempbytedata);
        return dataline;
    }

    public boolean isPut(){    //该方法用于利用post进行注册账号的情况判断
        return put;            //put值是只要利用post请求进行账号注册那么put为true，否则为false
    }

    public String getPath(){
        return path;
    }
    public String getHost(){
        return host;
    }
    public String getAccept(){
        return accept;
    }
    public String getUpgrade(){
        return upgrade;
    }
    public String getAccept_language(){
        return accept_language;
    }
    public String getAccept_encoding(){
        return accept_encoding;
    }
    public String getCache_control(){
        return cache_control;
    }
    public String getConnection(){
        return connection;
    }
    public String getReferer(){
        return referer;
    }
    public String getCookie(){
        return cookie;
    }
    public String getUser_agent(){
        return user_agent;
    }
    public String getMethod(){
        return method;
    }
    public String getVersion(){
        return version;
    }
    public String getCharset(){
        return charset;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getCompletePath(){
        String path;
        if(getPath().equals("/")){
            path="src/net/xufang/resource/Sophon.html";
        }
        else{
            path="src/net/xufang/resource"+getPath();
        }
        return path;
    }
    public String getContent(){
        return content;
    }
}
