package Server;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server đang lắng nghe kết nối...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Kết nối từ: " + clientSocket.getInetAddress());

            // Tạo một luồng riêng cho mỗi client
            Thread clientThread = new ClientHandler(clientSocket);
            clientThread.start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Nhận dữ liệu từ client: " + inputLine);
                String[] tokens = inputLine.split(" ");

                if (tokens.length != 3) {
                    out.println("Lỗi: Định dạng không hợp lệ");
                } else {
                    try {
                        double a = Double.parseDouble(tokens[0]);
                        double b = Double.parseDouble(tokens[2]);
                        String operator = tokens[1];
                        double result = 0;

                        switch (operator) {
                            case "+":
                                result = a + b;
                                break;
                            case "-":
                                result = a - b;
                                break;
                            case "*":
                                result = a * b;
                                break;
                            case "/":
                                if (b != 0) {
                                    result = a / b;
                                } else {
                                    out.println("Lỗi: Chia cho 0");
                                    continue;
                                }
                                break;
                            default:
                                out.println("Lỗi: Toán tử không hợp lệ");
                                continue;
                        }

                        out.println(result);
                        out.flush();
                    } catch (NumberFormatException e) {
                        out.println("Lỗi: Định dạng không hợp lệ");
                    }
                }
            }

            clientSocket.close();
            System.out.println("Kết nối đã đóng");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
