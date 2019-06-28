package net.xufang.serverthread;

import net.xufang.util.Request;
import net.xufang.util.Respond;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket;
    private InputStream in;

    public ServerThread(Socket socket){
        this.socket=socket;
    }


    //run方法没采用死循环，但与客户端的连接却还是保持着的，不采用循环是怕重复new request与respond会在没有数据输入时发生错误
    @Override
    public void run(){
            Request request = new Request(socket);
            Respond respond = new Respond(socket, request);
        /*try {
            socket.close();             //这里直接关闭socket是因为不想让浏览器一直处在等待服务器响应状态
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
