package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {
					// 5. Data 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // Blocking - return : 읽은 byte Size

					if (readByteCount == -1) { // 다 읽으면 상대편에서 소켓 끊음
						// 정상종료 : remote socket close()
						// 메소드를 통해 정상적으로 소켓을 닫은 경우
						System.out.println("[server] Closed By Client!!!!");
						break;
					}

					String data = new String(buffer, 0, readByteCount, "UTF-8"); // buffer -> String : 0번째부터 size 까지
					System.out.println("[server] Received : " + data);

					// 6. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));
					
					// timeout 테스트 위해 delay
					try {
						Thread.sleep(1000);;
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (SocketException e) {
				System.out.println("[server] abnormal closed by client");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 7. 자원 정리 (소켓 닫기)
				try {
					if (socket != null && !socket.isClosed()) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
