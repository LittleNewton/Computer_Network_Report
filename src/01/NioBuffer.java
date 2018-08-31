import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * ������(buffer):��java NIO�и������ݵĴ�ȡ���������������飬���ڴ洢��ͬ���͵Ļ����� ���������ͣ� ByteBuffer
 * CharBuffer IntBuffer ShortBuffer LongBuffer DoubleBuffer
 * 
 * ���ķ���:put()��get()
 * 
 * �������ԣ� capacity:��������ʾ��������������ݵĴ洢���� limit:���ޣ���ʾ�������п��Բ������ݵĴ�С��limit�����ݲ��ܽ��ж�д��
 * position��λ�ã���ʾ�����������ڲ������ݵ�λ��
 * 
 * mark():��� reset():�ָ���mark��λ��
 *
 * @author dxx
 * @version 2017-11-02 ����7:46:38
 */
public class NioBuffer {
    String str = "helloworld";

    @Test
    public void run() {
        // 1.����һ��ָ����С�Ļ�����
        ByteBuffer bb = ByteBuffer.allocate(1024);
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());

        // 2.��������
        bb.put(str.getBytes());
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());// λ�ñ�Ϊ10

        // 3.�л���ȡ����ģʽ
        bb.flip();
        System.out.println(bb.capacity());// ��������С��ȻΪ1024
        System.out.println(bb.limit());// �ɶ�ȡ����Ϊ10�����ֽ�
        System.out.println(bb.position());// λ���л���0�ˣ����Դ�0��ʼ��ȡ

        // 4.��ȡ����
        byte[] by = new byte[bb.limit()];
        bb.get(by);// ��ȡ���������ɶ�ȡ���������ݣ�Ҳ����10��,�����by������
        // System.out.println(by);
        System.out.println(new String(by, 0, by.length));
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());
        try {
            bb.get();// �ٶ��Ļ���Խ����
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5.rewind() �����ظ�������
        bb.rewind();
        System.out.println(bb.capacity());
        System.out.println(bb.limit());
        System.out.println(bb.position());// λ�ñ�Ϊ0�ˣ�˵���ֿ��Զ���

        // 6.clear()����ջ����������ǻ�������������Ȼ���ڣ����Ǵ��ڡ�������״̬��
        bb.clear();
        System.out.println(bb.capacity());
        System.out.println(bb.limit());// ָ��ȫ���ص���ԭʼ״̬����֪���ж�������
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
        System.out.println(bb.position());// ���ڶ����ֽ���

        // ���
        bb.mark();

        bb.get(by, 2, 3);
        System.out.println(new String(by, 2, 3));
        System.out.println(bb.position());// ���ڶ����ֽ���

        // ����
        bb.reset();
        System.out.println(bb.position());// λ���ֻص���Ǵ�
    }

    @Test
    public void run3() {
        // ��ֱ�ӻ�����:ͨ��allocate�����������仺��������������������JVM���ڴ���
        ByteBuffer bb = ByteBuffer.allocate(1024);
        // ֱ�ӻ�����:ͨ��allocateDirect������������ֱ�ӻ��������������������������ڴ��У��������Ч��
        ByteBuffer bb2 = ByteBuffer.allocateDirect(1024);
    }
}