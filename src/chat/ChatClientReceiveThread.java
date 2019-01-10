package chat;

import java.io.*;
import java.net.*;

public class ChatClientReceiveThread extends Thread {
	private Socket socket;
	private BufferedReader br;

	public ChatClientReceiveThread(Socket socket) {
		this.socket = socket;
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
			System.out.println("클라이언트 종료");
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
