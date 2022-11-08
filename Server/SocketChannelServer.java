//import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//import javafx.scene.effect.Light.Spot;
//import java.util.*;
//import java.net.InetAddress;
//import java.net.SocketException;
class network extends Thread {


    public void run()
    {
        try
        {
            DatagramSocket ds = new DatagramSocket(1234);
            byte[] receive = new byte[65535];

            DatagramPacket DpReceive = null;
            while (true)
            {
                DpReceive = new DatagramPacket(receive, receive.length);

                ds.receive(DpReceive);
                System.out.println("Thread working is " + Thread.currentThread().getId());

                System.out.println("Data " + data(receive));

                //Thread.sleep(10000);
                if (data(receive).toString().equals("bye"))
                {
                    System.out.println("Client sent bye.....EXITING");
                    break;
                }

                receive = new byte[65535];

            }
            ds.close();

        }
        catch(Exception e)
        {
            System.out.println("Exception caught thread stopped working");
        }
    }
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

}
public class SocketChannelServer
{
    public static void main(String[] args)
    {
        network obj1 = new network();
        obj1.start();
        network obj2 = new network();
        obj2.start();
        network obj3 = new network();
        obj3.start();

    }
}
