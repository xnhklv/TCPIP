package tcpip1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		
		ServerSocket serversocket = null;
		Socket socket = null;
		InputStream in= null;
		InputStreamReader inr = null;
		BufferedReader br = null;
		try {
			serversocket = new ServerSocket(7777);
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println(ia.getHostName());
			System.out.println(ia.getHostAddress());
			System.out.println("Start Server.........");
			
			
			while(true) {
				
		        socket = serversocket.accept();
				System.out.println("Client Connected...");
				
				//Receive Data.....
				in = socket.getInputStream();
				inr = new InputStreamReader(in);
				br = new BufferedReader(inr);
				String receivestr = br.readLine();
				System.out.println(receivestr);
				System.out.println("End Server......");

			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
				inr.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
}
