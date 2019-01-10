package chat.client.win;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import chat.ChatClientReceiveThread;

public class ChatClientApp {

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;

		try {
			while (true) {
				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				name = scanner.nextLine();

				if (!name.isEmpty()) {
					break;
				}

				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}

			scanner.close();

			// JOIN 처리 - 서버랑 연결
			// Response = join:ok - 윈도우 띄우기
			socket = new Socket();
			socket.connect(new InetSocketAddress("192.168.56.1", 5000));
		
			ChatWindow cw = new ChatWindow(name);

			new ChatClientReceiveThread(socket, cw).start();
			
			cw.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
