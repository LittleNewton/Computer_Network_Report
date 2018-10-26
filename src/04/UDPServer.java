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

        // (3) 接收数据
        System.out.println("服务端开始监听！~~~~");
        socket.receive(packet);

        // (4) 处理数据
        System.out.println("服务端" + new String(buf, 0, buf.length));

        // 2. 返回数据
        DatagramPacket p = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
        socket.send(p);
        socket.close();
    }

}