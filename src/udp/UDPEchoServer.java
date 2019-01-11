package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPEchoServer {
	private static final int PORT = 6000;
	private static final int BUFFER_SIZE = 1024; // 상수는 바깥으로 빼는게 좋은 습관

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			// 1. socket 생성
			socket = new DatagramSocket(PORT); // InetAddress 클래스 안써도 됨

			while (true) {
				// 2. 데이터(패킷) 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE); // buffer, buffer
																										// size
				socket.receive(receivePacket);

				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();

				String message = new String(data, 0, length, "UTF-8");
				System.out.println("[server] received : " + message);
				
				// 3. 데이터 전송
				byte[] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
				// DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));
				
				socket.send(sendPacket);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && !socket.isClosed()) {
				socket.close(); // udp는 비연결형이라 close()할때 Exception이 발생하지 않음
			}
		}
	}

}
