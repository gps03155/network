package chat.client.win;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.ChatClientReceiveThread;

public class ChatClientApp {
	private static String IP = "192.168.56.1";
	private static int PORT = 5000;

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;

		try {
			// JOIN 처리 - 서버랑 연결
			// Response = join:ok - 윈도우 띄우기
			socket = new Socket();
			socket.connect(new InetSocketAddress(IP, PORT));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw= new PrintWriter(socket.getOutputStream(), true);
		
			while (true) {
				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				name = scanner.nextLine();

				if (!name.isEmpty()) {
					pw.println("join:" + name);
					
					break;
				}

				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}

			scanner.close();

			ChatWindow cw = new ChatWindow(name, pw);

			new ChatClientReceiveThread(socket, cw).start();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
//		finally {
//			try {
//				if (socket != null && !socket.isClosed()) {
//					socket.close();
//				}
//			} catch (IOException e) {
//				System.out.println(e.getMessage());
//			}
//		}
	}

}
