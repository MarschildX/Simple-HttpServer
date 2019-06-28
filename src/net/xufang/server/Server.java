package net.xufang.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import net.xufang.serverthread.*;

public class Server extends Thread{
    private ExecutorService socketPool;
    private ServerSocket serverSocket;
    private int port;                  //暂时还没作用，默认使用23333端口

    public Server(int port){
        try{
            this.port=port;
            serverSocket=new ServerSocket(port);
            socketPool= Executors.newFixedThreadPool(8);//给线程池开了8个线程，因为查了资料，IO密集型任务给线程池分配的线程数最好是cpu核心数的两倍。
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){                 //采用轮询机制检查是否有用户连入，考虑到可能比较耗资源，所以创建了server的线程
            createSocket();
        }
    }


    public void createSocket(){//创建与客户端通信的线程
            Socket clientsocket = new Socket();
            try {
                clientsocket = serverSocket.accept();
                System.out.println("accept a new socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Runnable socketthread = new ServerThread(clientsocket);
            socketPool.submit(socketthread);
    }


    public static void main(String[] args){
        Server XuFangServer=new Server(23333);
        Thread serverThread=new Thread(XuFangServer);
        serverThread.start();
        System.out.println("----XuFangServer has been started----");
    }
}
