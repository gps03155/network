package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	private static final int PORT = 5000;
	public static int count;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listwriters;
		
		try {
			serverSocket = new ServerSocket();
			listwriters = new ArrayList<Writer>();
			
			// 1-1 set option SO_REUSEADDR (종료 후 빨리 바인딩을 하기 위해서)
			serverSocket.setReuseAddress(true);
			
			String hostname = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostname, PORT));
			System.out.println("서버 바인딩");
			
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("클라이언트 접속");
				System.out.println("현재 클라이언트 접속 수 : " + ++count);
				
				new ChatServerThread(socket, listwriters).start();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
