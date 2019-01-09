package http; // kr.ac.sungkyul.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());

			// get IOStream
			OutputStream outputStream = socket.getOutputStream(); // 보내야 할 것들이 문자열, 바이트 형식임 - 바이트로 보내는게 편함
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			String request = null;

			while (true) {
				String line = br.readLine();

				// 브라우저가 연결을 끊은 경우
				if (line == null) {
					break;
				}

				// header만 읽을 경우
				// header와 body는 개행 전후로 구분함
				if (line.equals("")) {
					break;
				}

				// header의 첫 줄만 출력
				if (request == null) {
					request = line;
				}

				// System.out.println("InputStream : " + line);
			}

			consoleLog(request);

			String[] tokens = request.split(" ");

			if (tokens[0].equals("GET")) {
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			} else { // POST , DELETE, PUT, ETC 명령
						// consoleLog("bad request : " + request);
				response400Error(outputStream, tokens[2]);

				/*
				 * HTTP/1.0 404 BAD Request Content-Type:text/html; charset=utf-8\r\n
				 * 
				 * html 400 읽기
				 */
			}

			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
//			outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
//			outputStream.write("Content-Type:text/html; charset=utf-8\r\n".getBytes("UTF-8"));
//			outputStream.write("\r\n".getBytes()); // 개행을 전후로 header, body 구분
//			outputStream.write("<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes("UTF-8"));

		} catch (Exception ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	public void consoleLog(String message) {
		System.out.println("[RequestHandler#" + getId() + "] " + message);
	}

	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException {
		if (url.equals("/")) {
			url = "/index.html";
		}

		File file = new File("./webapp" + url);

		if (!file.exists()) {
			response404Error(outputStream, protocol);
			/*
			 * HTTP/1.0 404 File Not Found\r\n Content-Type:text/html; charset=utf-8\r\n
			 * 
			 * 404html로 내용 보내기
			 */

			return;
		}

		// java 2.7 - nio api : 입출력시 예외처리 안해도 괜찮음 내부적으로 해결함
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath()); // css 적용

		// 응답
		outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
		outputStream.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8")); // contentType =
																										// text/html
		outputStream.write("\r\n".getBytes()); // 개행을 전후로 header, body 구분
		outputStream.write(body);
	}

	public void response404Error(OutputStream outputStream, String protocol) {
		File file = null;
		byte[] body = null;
		String contentType = null;

		System.out.println(protocol);

		try {
			file = new File("./webapp/error/404.html");
			body = Files.readAllBytes(file.toPath());
			contentType = Files.probeContentType(file.toPath());

			outputStream.write((protocol + " 404 File Not Found\r\n").getBytes("UTF-8"));
			outputStream.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8")); // contentType
			outputStream.write("\r\n".getBytes());
			outputStream.write(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void response400Error(OutputStream outputStream, String protocol) {
		File file = null;
		byte[] body = null;
		String contentType = null;

		System.out.println(protocol);

		try {
			file = new File("./webapp/error/400.html");
			body = Files.readAllBytes(file.toPath());
			contentType = Files.probeContentType(file.toPath());

			outputStream.write((protocol + " 400 Bad Request\r\n").getBytes("UTF-8"));
			outputStream.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8")); // contentType
			outputStream.write("\r\n".getBytes());
			outputStream.write(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
