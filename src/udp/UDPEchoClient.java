package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String IP = "192.168.56.1";
	private static final int PORT = 6000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		Scanner sc = null;
		DatagramSocket socket = null;

		try {
			// 1. 키보드 연결
			sc = new Scanner(System.in);

			// 2. 소켓 생성
			socket = new DatagramSocket();

			while (true) {
				// 3. 사용자 입력 받기
				System.out.print(">> ");
				String message = sc.nextLine();

				if (message.equals("quit")) {
					System.out.println("연결종료");
					break;
				}

				// 4. 메시지 전송
				byte[] data = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(IP, PORT));
				
				socket.send(sendPacket);
				
				// 5. 메시지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println(message);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				sc.close();
			}
			
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		}
	}

}
