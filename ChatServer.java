import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    List<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    public void sendAll (String message) {
        for (Client client : clients) {
            client.receive(message);
        }
    }

    public void run() throws IOException {
        while (true) {
            System.out.println("Ожидание...");
            try{
                // ждем клиента из сети
                Socket socket = serverSocket.accept();
                System.out.println("Клиент Подключен!");
                // создаем клиента на своей стороне
                clients.add(new Client(socket,this));
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}
