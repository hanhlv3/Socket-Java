package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		   	Socket socket = new Socket("localhost", 12345);
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	        System.out.println("Nhập số a:");
	        double a = Double.parseDouble(stdin.readLine());
	        System.out.println("Nhập toán tử (+, -, *, /):");
	        String operator = stdin.readLine();
	        System.out.println("Nhập số b:");
	        double b = Double.parseDouble(stdin.readLine());

	        out.println(a + " " + operator + " " + b);
	        out.flush();
	        String response;
	        while ((response = in.readLine()) != null) {
	            System.out.println("Kết quả: " + response);
	            break;
	        }
	        System.out.println("Le van hanh");
	        socket.close();
	}

}
