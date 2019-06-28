package net.xufang.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileUtil {
    private FileInputStream in;
    private String path;
    private File file;
    private OutputStream out;
    private String wholefilename;
    private long contentlength;

    public FileUtil(String path,OutputStream out){
        this.path=path;
        this.out=out;
        if(path.equals("/")){
            wholefilename="src/net/xufang/resource/Sophon.html";
        }
        else{
            wholefilename="src/net/xufang/resource"+path;
        }
    }

    public void parserAndSend(){    //对请求头的资源路径进行判断并发送数据
        String temppath="src/net/xufang/resource";
        if(path.equals("/")){
            file=new File("src/net/xufang/resource/Sophon.html");
            try {
                in = new FileInputStream(file);
                byte[] bytedata = new byte[1024];
                int length = -1;
                while ((length = in.read(bytedata)) != -1) {
                    out.write(bytedata,0,length);
                    out.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            file=new File(temppath+path);
            try {
                in = new FileInputStream(file);
                byte[] bytedata = new byte[1024];
                int length = -1;
                while ((length = in.read(bytedata)) != -1) {
                    out.write(bytedata,0,length);
                    out.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean isFileExist(){
        File tmpfile;
        String temppath="src/net/xufang/resource";
        if(path.equals("/")){
            tmpfile=new File("src/net/xufang/resource/Sophon.html");
            if(tmpfile.exists()){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            tmpfile=new File(temppath+path);
            if(tmpfile.exists()){
                return true;
            }
            else{
                return false;
            }
        }
    }

    public long getFileLength(){
        String temppath="src/net/xufang/resource";
        File tmpfile;
        if(path.equals("/")){
            tmpfile=new File("src/net/xufang/resource/Sophon.html");
            contentlength=tmpfile.length();
        }
        else{
            tmpfile=new File(temppath+path);
            contentlength=tmpfile.length();
        }
        return contentlength;
    }

    public String getLastModify(){        //得到文件最后一次修改时间
        String temppath="src/net/xufang/resource";
        File tmpfile;
        if(path.equals("/")){
            tmpfile=new File("src/net/xufang/resource/Sophon.html");
        }
        else{
            tmpfile=new File(temppath+path);
        }
        Calendar cal=Calendar.getInstance();
        long time=tmpfile.lastModified();
        cal.setTimeInMillis(time);
        SimpleDateFormat sf=new SimpleDateFormat("E, dd MM yy HH:mm:ss");
        String sftime=sf.format(cal.getTime());
        sftime=sftime+" GMT";
        return sftime;
    }

    public String returnFileType(){        //返回文件类型，目前还不完善
        if(wholefilename.contains(".jpg")){
            return "image/jpeg";
        }
        else if(wholefilename.contains(".html")){
            return "text/html; charset=utf-8";
        }
        else if(wholefilename.contains(".png")){
            return "image/png";
        }
        else{
            return "txt/html";
        }
    }

    public void sendNotFound(){        //当文件不存在时传送一个404的页面
        String tmppath="src/net/xufang/resource/";
        File file=new File(tmppath+"404NotFound.html");
        byte[] databyte=new byte[256];
        int length=-1;
        try {
            in = new FileInputStream(file);
            while((length=in.read(databyte,0,256))!=-1){
                out.write(databyte,0,length);
                out.flush();
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
