package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private List<Writer> listwriters;
	private PrintWriter pw;
	private BufferedReader br;

	public ChatServerThread(Socket socket, List<Writer> listwriters) {
		this.socket = socket;
		this.listwriters = listwriters;
	}

	@Override
	public void run() {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		System.out.println("클라이언트 연결 : " + inetSocketAddress.getAddress().getHostAddress() + " " + socket.getPort());

		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			pw = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
				String request = br.readLine();
				System.out.println("[server] : " + request);

				if (request == null) {
					System.out.println("클라이언트 연결 종료");
					System.out.println("현재 클라이언트 접속 수 : " + --ChatServer.count);
					
					doQuit(pw);
					break;
				}

				// 프로토콜 분석
				String[] tokens = request.split(":");

				if (tokens[0].equals("join")) {
					doJoin(tokens[1], pw);
				} else if (tokens[0].equals("message")) {
					if (tokens.length == 1) {
						doMessage("");
					} else {
						doMessage(tokens[1]);
					}
				} else if (tokens[0].equals("quit")) {
					doQuit(pw);
				} else {
					System.out.println("에러 : 알수없는 요청 " + tokens[0]);
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
				e.printStackTrace();
			}
		}
	}

	// 본인 닉네임 제외하고 다른 사람이 들어왔을 경우 들어왔다고 알려줌
	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;

		pw.println("join:ok");
		
		// 먼저 다른 클라이언트에게 새로 접속한 클라이언트 닉네임을 보냄
		broadcast("[" + this.nickname + "]" + "님이 들어오셨습니다.");	
		
		// 전송 후 PrintWriter 객체 저장
		addWriter(writer);
	}

	private void addWriter(Writer writer) {
		synchronized (listwriters) {
			listwriters.add(writer);
		}
	}

	private void broadcast(String data) {
		synchronized (listwriters) {
			for (Writer writer : listwriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				// System.out.println("전송 : " + data);
			}
		}
	}

	private void doMessage(String message) {
		broadcast("[" + nickname + "] : " + message);
	}

	private void doQuit(Writer writer) {
		removeWriter(writer);

		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}

	private void removeWriter(Writer writer) {
		synchronized (listwriters) {
			listwriters.remove(writer);
		}
	}

}
