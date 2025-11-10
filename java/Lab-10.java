import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to the server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            String clientMsg, serverMsg;

            while (true) {

                System.out.print("You: ");
                clientMsg = keyboard.readLine();
                out.println(clientMsg);

                if (clientMsg.equalsIgnoreCase("bye")) {
                    System.out.println("Chat ended by client.");
                    break;
                }

                // Receive message from server
                serverMsg = in.readLine();
                if (serverMsg == null || serverMsg.equalsIgnoreCase("bye")) {
                    System.out.println("Server ended the chat.");
                    break;
                }
                System.out.println("Server: " + serverMsg);
            }

            // Close all connections
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}