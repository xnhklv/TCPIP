package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

	ServerSocket serversocket;
	Socket socket;
	Scanner scanner;
	
	public ServerChat() {}
	public ServerChat(int port) {
		try {
			serversocket = new ServerSocket(port);
			System.out.println("Ready Server.....");
			start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
				serversocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
	}
	private void start() throws IOException {
		socket = serversocket.accept();
		scanner = new Scanner(System.in);
		
		
		Receiver receiver = new Receiver();
		receiver.start();
		
		
		while(true) {
			Sender sender = new Sender();
			Thread t = new Thread(sender);
			System.out.println("Input Server Message.....");
			String msg = scanner.nextLine();
			if(msg.equals("q")) {
				scanner.close();
				break;
			}
			
			sender.setSendMsg(msg);
			t.start();
		}
		System.out.println("Exit ServerChat....");
		}
	class Sender implements Runnable{
		OutputStream out;
		DataOutputStream dout;
		String msg;
		
		public Sender() throws IOException {
			out = socket.getOutputStream();
			dout = new DataOutputStream(out);
		}

		public void setSendMsg(String msg) {
			this.msg=msg;
		}
		
		public void close() throws IOException {
			dout.close();
			out.close();
		}
		@Override
		public void run() {
			try {
				
				if(dout !=null) {
					dout.writeUTF(msg);
				}
				} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	class Receiver extends Thread{

		InputStream in;
		DataInputStream din;
		
		public Receiver() throws IOException {
			in = socket.getInputStream();
			din = new DataInputStream(in);
		}
		
		public void close() throws IOException {
			din.close();
			in.close();
			
		}
		
		@Override
		public void run() {
			while(true) {
				String msg;
				try {
					msg = din.readUTF();
					System.out.println(msg);
				} catch (IOException e) {
				System.out.println("Exit Server User......");
				break;
				}
				
			}
		}
	}
}
