package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 6000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
				
		try {
			serverSocket = new ServerSocket();
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(localhost, PORT));
			System.out.println("서버 바인딩");

			Socket socket = serverSocket.accept();
			System.out.println("클라이언트 연결");

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			while (true) {
				String str = br.readLine();
				
				if(str == null) {
					System.out.println("연결 종료");
					break;
				}

				System.out.println("Received : " + str);

				pw.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
