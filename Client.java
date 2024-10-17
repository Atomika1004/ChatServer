import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Socket socket;
    ChatServer chatServer;
    Scanner in;
    PrintStream out;

    public Client(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
        new Thread(this).start();
    }

    public void receive (String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
             in = new Scanner(is);
             out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("ƒобро пожаловать в сетевой чат!");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                chatServer.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}