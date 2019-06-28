package net.xufang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LoginUtil {
    private OutputStream out;
    private Request request;

    public LoginUtil(Request request,OutputStream out){
        this.request=request;
        this.out=out;
    }

    public boolean validate(){       //验证账户与密码
        boolean flag=true;
        File file=new File("src/net/xufang/resource/logindata.txt");
        String logindata = new String();
        try {
            FileInputStream fin = new FileInputStream(file);

            int length = -1;
            byte[] tmpdata = new byte[128];
            String databyte;
            while ((length = fin.read(tmpdata)) != -1) {
                databyte = new String(tmpdata);
                logindata += databyte;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
            String user,pawd;
            user=request.getUsername();
            pawd=request.getPassword();
            int start,end;
            String focus;
            if(logindata.contains(user)){
                start=logindata.indexOf(user);
                end=logindata.indexOf(";",start);
                start-=9;
                focus=logindata.substring(start,end);
                String tmppassword;
                start=focus.indexOf("password=");
                start+=9;
                tmppassword=focus.substring(start);
                if(tmppassword.equals(pawd)){
                    flag=true;
                }
                else{
                    flag=false;
                }
            }
            else {
                flag=false;
            }
            return flag;
    }

    public void send(){
        boolean staus=this.validate();
        int length=-1;
        byte[] datasend=new byte[256];
        File tmpfile;
        FileInputStream fin;
        if(staus){
            tmpfile=new File("src/net/xufang/resource/loginsuccessful.html");
            try {
                fin = new FileInputStream(tmpfile);
                while((length=fin.read(datasend,0,256))!=-1){
                    out.write(datasend,0,length);
                    out.flush();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            tmpfile=new File("src/net/xufang/resource/loginfailed.html");
            try {
                fin = new FileInputStream(tmpfile);
                while((length=fin.read(datasend,0,256))!=-1){
                    out.write(datasend,0,length);
                    out.flush();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public long getContentLength(){
        File tmpfile;
        if(this.validate()){
            tmpfile=new File("src/net/xufang/resource/loginsuccessful.html");
            return tmpfile.length();
        }
        else{
            tmpfile=new File("src/net/xufang/resource/loginfailed.html");
            return tmpfile.length();
        }
    }

}
