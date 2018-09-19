import java.nio.*;
import java.io.IOException;

public class ByteBuffer_read {
    public static void main(String[] args) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(256);
        while (true) {
            int c = System.in.read();

            if (c == -1)
                break;

            buf.put((byte) c);

            if (c == '\n') {
                buf.flip();
                byte[] content = new byte[buf.limit()];
                buf.get(content);
                System.out.print(new String(content));
                buf.clear();
            }
        }
    }
}