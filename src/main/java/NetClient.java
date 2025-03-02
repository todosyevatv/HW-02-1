//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NetClient extends JFrame implements KeyListener {
    final String serverIP = "127.0.0.1";
    final int serverPort = 1234;
    JTextArea textArea;
    JScrollPane scrollPane;
    InputStreamReader in;
    PrintWriter out;

    NetClient() {
        super("Simple Chat client");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(3);
        this.textArea = new JTextArea();
        this.textArea.setBackground(Color.BLACK);
        this.textArea.setForeground(Color.WHITE);
        this.textArea.setEditable(false);
        this.textArea.setMargin(new Insets(10, 10, 10, 10));
        this.scrollPane = new JScrollPane(this.textArea);
        this.add(this.scrollPane);
        this.connect();
    }

    void connect() {
        try {
            Socket socket = new Socket("127.0.0.1", 1234);
            this.in = new InputStreamReader(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream());
            this.textArea.addKeyListener(this);
        } catch (IOException var2) {
            this.textArea.setForeground(Color.RED);
            this.textArea.append("Server 127.0.0.1 port 1234 NOT AVAILABLE");
            var2.printStackTrace();
        }

        (new Thread() {
            public void run() {
                while(true) {
                    try {
                        NetClient.this.addCharToTextArea((char)NetClient.this.in.read());
                    } catch (IOException var2) {
                        NetClient.this.textArea.setForeground(Color.RED);
                        NetClient.this.textArea.append("\nCONNECTION ERROR");
                        var2.printStackTrace();
                        return;
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        (new NetClient()).setVisible(true);
    }

    public void keyPressed(KeyEvent arg0) {
    }

    public void keyReleased(KeyEvent arg0) {
    }

    public void keyTyped(KeyEvent arg0) {
        this.out.print(arg0.getKeyChar());
        this.out.flush();
        System.out.print(arg0.getKeyChar());
        this.addCharToTextArea(arg0.getKeyChar());
    }

    void addCharToTextArea(char c) {
        this.textArea.append(String.valueOf(c));
        this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
    }
}
