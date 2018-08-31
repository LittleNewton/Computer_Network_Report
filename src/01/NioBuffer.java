import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 缓冲区(buffer):在java NIO中负责数据的存取，缓冲区就是数组，用于存储不同类型的缓冲区 缓冲区类型： ByteBuffer
 * CharBuffer IntBuffer ShortBuffer LongBuffer DoubleBuffer
 * 
 * 核心方法:put()和get()
 * 
 * 核心属性： capacity:容量，表示缓冲区中最大数据的存储容量 limit:界限，表示缓冲区中可以操作数据的大小（limit后数据不能进行读写）
 * position：位置，表示缓冲区中正在操作数据的位置
 * 
 * mark():标记 reset():恢复到mark的位置
 *
 * @author dxx
 * @version 2017-11-02 下午7:46:38
 */
public class NioBuffer {
    String str = "helloworld";

    @Test
    public void run() {
        // 1.分配一个指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());

        // 2.存入数据
        bb.put(str.getBytes());
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());// 位置变为10

        // 3.切换读取数据模式
        bb.flip();
        System.out.println(bb.capacity());// 缓冲区大小仍然为1024
        System.out.println(bb.limit());// 可读取数量为10个的字节
        System.out.println(bb.position());// 位置切换到0了，可以从0开始读取

        // 4.读取数据
        byte[] by = new byte[bb.limit()];
        bb.get(by);// 获取到缓冲区可读取的所有数据（也就是10）,存放在by数组中
        // System.out.println(by);
        System.out.println(new String(by, 0, by.length));
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());
        try {
            bb.get();// 再读的话就越界了
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5.rewind() ：可重复读数据
        bb.rewind();
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());// 位置变为0了，说明又可以读了

        // 6.clear()：清空缓冲区，但是缓冲区的数据依然存在，但是处于“被遗忘状态”
        bb.clear();
        System.out.println(bb.capacity());
        System.out.println(bb.limit());// 指针全部回到最原始状态，不知道有多少数据
        System.out.println(bb.position());
        System.out.println((char) bb.get());
    }

    @Test
    public void run2() {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put(str.getBytes());
        bb.flip();
        byte[] by = new byte[bb.limit()];
        bb.get(by, 0, 2);
        System.out.println(new String(by, 0, 2));
        System.out.println(bb.position());// 到第二个字节了

        // 标记
        bb.mark();

        bb.get(by, 2, 3);
        System.out.println(new String(by, 2, 3));
        System.out.println(bb.position());// 到第二个字节了

        // 重置
        bb.reset();
        System.out.println(bb.position());// 位置又回到标记处
    }

    @Test
    public void run3() {
        // 非直接缓冲区:通过allocate（）方法分配缓冲区，将缓冲区建立在JVM的内存中
        ByteBuffer bb = ByteBuffer.allocate(1024);
        // 直接缓冲区:通过allocateDirect（）方法分配直接缓冲区，将缓冲区建立在物理内存中，可以提高效率
        ByteBuffer bb2 = ByteBuffer.allocateDirect(1024);
    }
}