package net.xufang.util;

import java.io.*;

public class RegisterUtil {
    private Request request;
    private FileOutputStream fout;
    private OutputStream out;
    private String tmppath;

    public RegisterUtil(Request request,OutputStream out){
        this.request=request;
        this.out=out;
        tmppath="src/net/xufang/resource/";
        File file=new File(tmppath+"logindata.txt");
        try {
            fout = new FileOutputStream(file,true);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void register(){                //根据输入的数据进行注册
        String username=request.getUsername();
        String password=request.getPassword();
        String dataforwrite="";
        dataforwrite+="username=";
        dataforwrite+=username;
        dataforwrite+="&";
        dataforwrite+="password=";
        dataforwrite+=password;
        dataforwrite+=";\r\n";
        try {
            fout.write(dataforwrite.getBytes());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void send(){
        File file=new File(tmppath+"registersuccessful.html");
        try {
            FileInputStream fin = new FileInputStream(file);
            int length=-1;
            byte[] databyte=new byte[256];
            while((length=fin.read(databyte,0,256))!=-1){
                out.write(databyte,0,length);
                out.flush();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public long getContentLength(){
        File tmpfile=new File(tmppath+"registersuccessful.html");
        return tmpfile.length();
    }
}
