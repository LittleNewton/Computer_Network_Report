import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPServer {
    public static void main(String[] args) throws IOException {

        // 生成一个1MB的存储空间
        byte[] buf = new byte[1024];

        // 1. 接收数据
        // (1) 创建接受数据的数据包
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // (2) 创建UDP的Socket
        DatagramSocket socket = new DatagramSocket(2222);

        // 利用 i 这个变量进行若干次干扰！
        int i = 0;

        // (3) 接收数据
        while (true) {

            System.out.println("===Server 端开始监听===");
            socket.receive(packet);

            // (4) 处理数据
            System.out.println("服务端：" + new String(buf, 0, buf.length));

            if (i == 7) {
                // 2. 返回数据
                DatagramPacket p = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
                i = 0;
                socket.send(p);
            } else {
                for (i = 0; i < 7; i++) {
                    byte[] buf2 = new byte[1024]; // 发送一个错误的数组回去
                    DatagramPacket p = new DatagramPacket(buf2, buf2.length, packet.getAddress(), packet.getPort());
                    socket.send(p);
                    System.out.println("===Server 返回了一个错误的信息===");
                }
            }
        }
    }
}