package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1. server socket 생성
			// socket.close() 하면 내부의 스트림도 다 닫힘
			serverSocket = new ServerSocket();

			// 2. Binding (socket의 socket address(IP address + port)를 바인딩 함
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			
			serverSocket.bind(new InetSocketAddress(localhostAddress, PORT));
			System.out.println("[server] Binding " + localhostAddress + ":" + PORT);
			
			// 3. Accept (Client로부터 연결 요청을 기다림)
			// 여기서 block이 발생함 - accept를 받을 때까지 기다림
			Socket socket = serverSocket.accept(); // Blocking
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			System.out.println("[server] Connected by Client!!!!");
			System.out.println("[server] Remote : " + remoteHostAddress + ":" + remotePort); // Client 정보 - ip, port
			
		} catch (IOException e) {
			e.printStackTrace();
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
