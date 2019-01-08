package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		Socket socket = null;

		try {
			// 1. 소켓 생성
			socket = new Socket();

			// 2. 서버 연결
			// Binding이 필요없음 - 클라이언트 소켓은 안기다려도 됨
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] Connected");
			
			// 3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			// 4. 데이터 쓰기
			String data = "Hello World\n";
			os.write(data.getBytes("UTF-8"));
			
			// 5. 데이터 받기
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer); // Blocking
			
			if(readByteCount == -1) {
				System.out.println("[client] disconnect");
				return;
			}
			
			data = new String(buffer, 0, readByteCount, "UTF-8");
			System.out.println("[client] received : " + data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
