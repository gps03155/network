package chat.win;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	private static final int PORT = 5000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
		// 1. 서버 소겟 생성
		 serverSocket = new ServerSocket();
					
		// 2. 바인딩
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		serverSocket.bind( new InetSocketAddress( hostAddress, PORT ) );			
		Log( "연결 기다림 " + hostAddress + ":" + PORT );

		// 3. 요청 대기 
		while( true ) {
		   Socket socket = serverSocket.accept();
		   new ChatServerThread( socket ).start();
		}
		}
		catch(IOException e) {
			Log("error : "+ e );
		}finally {
			
				try {
					if(serverSocket!=null) {
					serverSocket.close();
					}
				} catch (IOException e) {
					Log("error : "+ e );
				
			}
		}
		
	}
	
	public static void Log(String message) {
		System.out.println("[Server#" + Thread.currentThread().getId()  + "] " + message);
	}
}