import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class ClientClass {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress addr = new InetSocketAddress("localhost", 1111);
        //  selectable channel for stream-oriented connecting sockets
        SocketChannel client = SocketChannel.open(addr);
        log("Connecting to Server on port 1111...");
        ArrayList<String> data = new ArrayList<String>();
        // create a ArrayList with raw list
        data.add("Facebook");
        data.add("Twitter");
        data.add("IBM");
        data.add("Google");
        data.add("GE Healthcare");
        for (String raw : data) {
            byte[] message = new String(raw).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);
            log("sending: " + raw);
            buffer.clear();
            // wait for 2 seconds before sending next message
            Thread.sleep(10000);
        }
        
        // close(): Closes this channel.
        // If the channel has already been closed then this method returns immediately.
        // Otherwise it marks the channel as closed and then invokes the implCloseChannel method in order to complete the close operation.
        client.close();
    }
    private static void log(String str) {
        
        System.out.println(str);
    }
}