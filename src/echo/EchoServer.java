package echo;

import java.io.IOException;
import java.net.*;

public class EchoServer {
	private static final int PORT = 6000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket();

			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(localhost, PORT));
			System.out.println("서버 바인딩 : " + localhost);

			while (true) { // 클라이언트 연결을 계속 받기 위해 무한 루프
				Socket socket = serverSocket.accept(); // thread 써야함
				System.out.println("Server Accept");
				
				Thread thread = new EchoServerReceiveThread(socket); // 다중 접속을 위한 thread 사용 - thread 생성자에 socket을 넘겨줌
				thread.start();
			}

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
