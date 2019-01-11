package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {
	private static final String IP = "192.168.56.1";
	private static final int PORT = 5000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner sc = null;
		
		try {
			socket = new DatagramSocket();
			sc = new Scanner(System.in);
			
			while(true) {
				System.out.print(">> ");
				String msg = sc.nextLine();
				
				if(msg.equals("quit")) {
					System.out.println("연결종료");
					break;
				}
				
				byte[] data = msg.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(IP, PORT));
				socket.send(sendPacket);
				
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);

				msg = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8"); // 인덱스 0부터 utf-8로 디코딩
				System.out.println(msg);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
