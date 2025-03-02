import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    Socket socket;
    ChatServer server;
    Scanner in;
    PrintStream out;

    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void printMessage(String message) {
        out.println(message);
    }

    public void run() {
        try {
            // читаем из сети и пишем в сеть
            out.println("Добро пожаловать в чат");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.printAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
