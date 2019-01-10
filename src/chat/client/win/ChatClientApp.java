package chat.client.win;

import java.util.Scanner;

public class ChatClientApp {

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while( true ) {
			
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
		ChatWindow cw = new ChatWindow(name);

		// new ChatClientThread(cw).start();
		cw.show();
	}

}
