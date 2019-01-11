package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import chat.client.win.ChatWindow;


public class ChatClientReceiveThread extends Thread {
	private Socket socket;
	private BufferedReader br;
	private ChatWindow cw;
	
	public ChatClientReceiveThread(Socket socket) {
		this.socket = socket;
	}			
	
	public ChatClientReceiveThread(Socket socket, ChatWindow cw) {
		this.socket = socket;
		this.cw = cw;
		
		cw.show();
	}
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			while (true) {
				String data = br.readLine();

				if (data == null) {	
					System.out.println("연결종료");
					break;
				}

				if (data.equals("join:ok")) {
					continue;
				} else {
					System.out.println(data);
				}
			}
		} catch (SocketException e) {
			System.out.println("클라이언트 종료" + e.getMessage());
		}

		catch (IOException e) {
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
