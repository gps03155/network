package chat.win;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.client.win.ChatWindow;

public class ChatClientApp {
	private static final String IP = "192.168.56.1";
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		String name = null;
		
		try {
			sc = new Scanner(System.in);
			socket = new Socket();
			
			socket.connect(new InetSocketAddress(IP, PORT));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			
			while(true) {
				System.out.println("대화명을 입력하세요.");
				System.out.println(">>> ");
				name = sc.nextLine();
				
				if(!name.isEmpty()) {
					pw.println("join:" + name);
					break;
				}
				
				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}
			
			sc.close();
			
			// join 처리
			// Response = "join:ok"
			ChatWindow cw = new ChatWindow(name, pw);
			new ChatClientThread(br, cw).start();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
