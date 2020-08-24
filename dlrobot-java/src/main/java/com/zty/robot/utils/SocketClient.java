package com.zty.robot.utils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

    public class SocketClient {

        public static String send(String question)throws Exception {
            String info = null;
            try {
                Socket socket = new Socket("127.0.0.1", 50007);
                //获取输出流，向服务器端发送信息
                OutputStream os = socket.getOutputStream();//字节输出流
                PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
                pw.write(question);
                pw.flush();
                socket.shutdownOutput();//关闭输出流
                InputStream is = socket.getInputStream();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {

//                    while ((info = in.readLine()) != null) {
//                        info+=info;
//                        //System.out.println("我是客户端，Python服务器说：" + info);
//                    }
                    info=in.readLine();
                    is.close();
                    in.close();
                }
                socket.close();
                return info;
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
             return info;
        }
}
