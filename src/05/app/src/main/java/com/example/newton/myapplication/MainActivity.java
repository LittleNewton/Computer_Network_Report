package com.example.newton.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import java.io.IOException;

// Java网络类库
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;

import android.view.View;

// Java的定时器类，通过多线程的方式实现定时做某事
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button button;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        button = findViewById(R.id.button);
    }

    // 这一部分代码继承自实验报告4，同样采取一个点击操作，发送并会回文一个字符串。
    public void onClick(View view) {

        new Thread() {
            @Override
            public void run() {
                super.run();

                // 开启一个Timer线程来做定时工作
                final Timer timer = new Timer();

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {

                            // 通过对 EditText 这个对象进行获取数据，得到一个字节数组
                            byte[] bytes = et.getText().toString().getBytes();

                            // 接收数据
                            InetAddress address = InetAddress.getByName("192.168.1.75");

                            // 1. 构造数据包
                            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 2222);


                            // 2. 创建数据报套接字并将其绑定到本地主机上的指定端口。
                            DatagramSocket socket = new DatagramSocket();

                            // 3. 从该套接字发送 packet
                            socket.send(packet);

                            // 建立一个空的 packet，用来放置接收到的ACK
                            // 这么做的目的是为了后期避免在线程之外无法引用 socket
                            final byte[] bytes1 = new byte[1024];
                            System.out.println(bytes1[0]);
                            DatagramPacket receiverPacket = new DatagramPacket(bytes1, bytes1.length);

                            socket.receive(receiverPacket);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText(new String(bytes1, 0, bytes1.length));
                                }
                            });

                            if (bytes[1] == bytes1[1]) {
                                System.out.println("正确接收到了");
                                cancel();
                                timer.cancel();
                                socket.close();
                            } else {
                                System.out.println("出错了！");
                            }

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (SocketException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 8);
                System.out.println("一切将要结束了");
            }
        }.start();
    }
}