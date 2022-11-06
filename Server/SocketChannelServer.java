import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerClass {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException 
        {

            // Selector: A multiplexor of SelectableChannel objects.
            // A selector may be created by invoking the open method of this class, which will use the system's default selector provider to create a new selector.
            // A selector may also be created by invoking the openSelector method of a custom selector provider. A selector remains open until it is closed via its close method.
            
            Selector selector = Selector.open(); // selector is open here

            // ServerSocketChannel: A selectable channel for stream-oriented listening sockets.
            // A server-socket channel is created by invoking the open method of this class.
            // It is not possible to create a channel for an arbitrary, pre-existing ServerSocket.
            
            ServerSocketChannel socket = ServerSocketChannel.open();
            
            // InetSocketAddress: This class implements an IP Socket Address (IP address + port number) It can also be a pair (hostname + port number),
            // in which case an attempt will be made to resolve the hostname.
            // If resolution fails then the address is said to be unresolved but can still be used on some circumstances like connecting through a proxy.

            InetSocketAddress addr = new InetSocketAddress("localhost", 1111);// Binds the channel's socket to a local address and configures the socket to listen for connections
            socket.bind(addr);// Adjusts this channel's blocking mode.
            socket.configureBlocking(false);
            int ops = socket.validOps();
            
            // SelectionKey: A token representing the registration of a SelectableChannel with a Selector.
            // A selection key is created each time a channel is registered with a selector.
            // A key remains valid until it is cancelled by invoking its cancel method, by closing its channel, or by closing its selector.

            SelectionKey selectKy = socket.register(selector, ops, null);

            while (true) 
                {
                    
                    log("Server waiting for new connection and buffer select...");// Selects a set of keys whose corresponding channels are ready for I/O operations
                    selector.select();// token representing the registration of a SelectableChannel with a Selector
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> i = keys.iterator();
                    while (i.hasNext()) 
                    {
                        SelectionKey myKey = i.next();// Tests whether this key's channel is ready to accept a new socket connection
                        if (myKey.isAcceptable()) 
                            {
                                SocketChannel client = socket.accept(); // Adjusts this channel's blocking mode to false
                                client.configureBlocking(false);     // Operation-set bit for read operations
                                client.register(selector, SelectionKey.OP_READ);
                                log("Connection Accepted: " + client.getLocalAddress() + "\n");   // Tests whether this key's channel is ready for reading
                            } 
                        else if (myKey.isReadable()) 
                            {
                                SocketChannel client = (SocketChannel) myKey.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(256);
                                client.read(buffer);
                                String result = new String(buffer.array()).trim();
                                log("Message received: " + result);
                                if (result.equals("GE Healthcare")) 
                                    {
                                        client.close();
                                        log("\nConnection closed. All data Successfully Received.");
                                        log("\nServer will keep running. Try running client again to establish new connection");
                                    }
                            }
                        i.remove();
                    }
                }
        }
    private static void log(String str) 
        {
            
            System.out.println(str);
        }
}