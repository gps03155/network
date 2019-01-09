package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String IP = "192.168.56.1";
	private static final int PORT = 6000;

	public static void main(String[] args) {
		Socket socket = null;

		try {
			socket = new Socket();

			socket.connect(new InetSocketAddress(IP, PORT));
			System.out.println("서버 접속");

			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			Scanner sc = new Scanner(System.in);

			while (true) {
				System.out.print(">> ");
				String input = sc.nextLine();
				pw.println(input);
				
				if(input.equals("exit")) {
					System.out.println("연결종료");
					break;
				}

				String str = br.readLine();
				System.out.println("<< " + str);
			}

		} catch (IOException e) {
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

}
