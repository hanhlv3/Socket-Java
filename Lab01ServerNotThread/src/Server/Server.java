/**
 * 
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = new ServerSocket(12345);
		System.out.println("Server is running");
		while(true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connect to " + clientSocket.getInetAddress());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			
			String inputLine;
			while ((inputLine= in.readLine()) != null) {
				System.out.println("Data from client: " + inputLine);
				String[] tokens = inputLine.split(" ");
				if(tokens.length != 3) {
					out.print("Lỗi định dạng");
					
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
                        
                        System.out.println("Result " + result);
                        out.println(result);
                        out.flush();
                        
					} catch (Exception e) {
						out.println("Lỗi: Định dạng không hợp lệ");
					}
				}
				
			}
			
			 clientSocket.close();
	         System.out.println("Kết nối đã đóng");
			
		}

	}
	
	

}
