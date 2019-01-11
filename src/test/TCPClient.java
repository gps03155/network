package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		Socket socket = null;

		try {
			// 1. 소켓 생성
			socket = new Socket();

			// 1-1. socket buffer size 확인
			int receiveBufferSize = socket.getReceiveBufferSize();
			int sendBuffersize = socket.getSendBufferSize();

			System.out.println(receiveBufferSize + ":" + sendBuffersize);

			// 1-2 socket buffer size 변경
			// 버퍼 사이즈 늘린다고 성능이 향상되는 것은 아님
			socket.setReceiveBufferSize(1024 * 10);
			socket.setSendBufferSize(1024 * 10);

			receiveBufferSize = socket.getReceiveBufferSize();
			sendBuffersize = socket.getSendBufferSize();

			System.out.println(receiveBufferSize + ":" + sendBuffersize);

			// 1-3 SO_NODELAY (Nagle Algorithm off)
			// delay : ACK를 기다리기 때문에 생김
			// true : delay가 없도록 하겠다
			socket.setTcpNoDelay(true);

			// 1-4 SO_TIMEOUT
			// read, write 모두 timeout 걸릴 수 있음
			// SocketTimeoutException : read에서만 발생함
			socket.setSoTimeout(1); // millisecond

			// 2. 서버 연결
			// Binding이 필요없음 - 클라이언트 소켓은 안기다려도 됨
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] Connected");

			// 3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			// 4. 데이터 쓰기
			String data = "Hello World\n"; // 항상 개행을 붙여서 해야함 - 보낼때는 항상 개행을 붙여야함
			os.write(data.getBytes("UTF-8"));

			// 5. 데이터 받기
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer); // Blocking

			if (readByteCount == -1) {
				System.out.println("[client] disconnect");
				return;
			}

			data = new String(buffer, 0, readByteCount, "UTF-8");
			System.out.println("[client] received : " + data);
			
		} catch (SocketTimeoutException e) { // read 할때만 발생
			System.out.println("[client] time out " + e.getMessage());
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
