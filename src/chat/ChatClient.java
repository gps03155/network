package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
	private static final String IP = "192.168.56.1";
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = null;
				
		try {
			socket = new Socket();
			sc = new Scanner(System.in);
			
			socket.connect(new InetSocketAddress(IP, PORT));
			System.out.println("서버 접속 :" + socket.getInetAddress().getHostAddress() + " " + socket.getPort());

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			
			System.out.print("닉네임 >> ");
			String nickname = sc.nextLine();
		
			pw.println("join:" + nickname);
			// System.out.println("닉네임 전송 : " + nickname);
			
			new ChatClientReceiveThread(socket).start();
			
			while(true) {
				String input = sc.nextLine();
				
				if(input.equals("quit")) {
					System.out.println("연결종료");
					break;
				}
				else {
					pw.println("message:" + input);
					// System.out.println("메시지 전송 : " + input);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}
}
