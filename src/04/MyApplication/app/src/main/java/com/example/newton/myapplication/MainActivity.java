package com.example.newton.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        button = findViewById(R.id.button);
    }

    public void onClick(View view) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                byte[] bytes = et.getText().toString().getBytes();
                try {
                    // 接收数据
                    InetAddress address = InetAddress.getByName("192.168.1.75");

                    // 1. 构造数据包
                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 2222);

                    // 2. 创建数据报套接字并将其绑定到本地主机上的指定端口。
                    DatagramSocket socket = new DatagramSocket();

                    // 3. 从此套接字发送数据报包。
                    socket.send(packet);

                    // 接收数据
                    // 1. 构造 DatagramPacket，用来接收长度为 length 的数据包。
                    final byte[] bytes1 = new byte[1024];
                    DatagramPacket receiverPacket = new DatagramPacket(bytes1, bytes1.length);
                    socket.receive(receiverPacket);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(new String(bytes1, 0, bytes1.length));
                        }
                    });

                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
