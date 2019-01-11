package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	private static final int PORT = 5000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(PORT);
			
			while(true) {
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				
				socket.receive(receivePacket);
				
				String msg = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println("[server] received : " + msg);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS a");
				String date = format.format(new Date());
				
				byte[] sendDate = date.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendDate, sendDate.length, new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));
				
				socket.send(sendPacket);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
