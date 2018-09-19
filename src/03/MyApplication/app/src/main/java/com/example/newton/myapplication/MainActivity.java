package com.example.newton.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.UnknownHostException;

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

    public void onClick(View view){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    // 1.创建监听指定服务器地址以及指定服务器监听的端口号
                    Socket socket = new Socket("192.168.1.79", 2222);

                    // 2.拿到客户端的socket对象的输出流发送给服务器数据
                    OutputStream os = socket.getOutputStream(); // 客户机的output

                    // 写入要发送给服务器的数据
                    os.write(et.getText().toString().getBytes());

                    // System.out.println(os.toString());

                    os.flush();
                    socket.shutdownOutput();    // OK, 不再需要写字了，准备发送吧！

                    // 拿到socket的输入流，这里存储的是服务器返回的数据
                    InputStream is = socket.getInputStream();

                    // 解析服务器返回的数据
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader bufReader = new BufferedReader(reader);
                    String s = null;
                    final StringBuffer sb = new StringBuffer();
                    while((s = bufReader.readLine()) != null){
                        sb.append(s);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(sb.toString());
                        }
                    });

                    // 3、关闭IO资源
                    bufReader.close();
                    reader.close();
                    is.close();
                    os.close();
                    socket.close();
                }  catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}