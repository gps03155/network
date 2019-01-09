package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		log("connected by client[" + inetRemoteSocketAddress.getAddress().getHostAddress() + ":"
				+ inetRemoteSocketAddress.getPort() + "]");

		try {
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			while (true) {
				String str = br.readLine();

				if (str == null) {
					System.out.println("연결 종료");
					break;
				}

				System.out.println("Received : " + str);

				pw.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() + "]" + log);
	}

}
