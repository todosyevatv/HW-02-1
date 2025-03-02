import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    ArrayList<Client> clients;
    ServerSocket server;

    public ChatServer() {
        clients = new ArrayList<>();
        try {
            // создаем серверный сокет на порту 1234
            server = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAll(String message) {
        for (Client client:clients) {
            client.printMessage(message);
        }
    }

    public void run() {
        while (true) {
            System.out.println("Waiting...");
            try {
                // ждем клиента
                Socket socket = server.accept();
                System.out.println("Client connected!");
                clients.add(new Client(socket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}
