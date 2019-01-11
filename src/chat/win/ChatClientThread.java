package chat.win;

import java.io.BufferedReader;
import java.io.IOException;

import chat.client.win.ChatWindow;

public class ChatClientThread extends Thread {
	private BufferedReader bufferedReader;
	private int i;
	private ChatWindow cw;
	
	public ChatClientThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
		this.i = 0;
	}
	
	public ChatClientThread(BufferedReader bufferedReader, ChatWindow cw) {
		this.bufferedReader = bufferedReader;
		this.cw = cw;
		cw.show();
		this.i = 1;
		
	}
	

	@Override
	public void run() {
		try {
			if(i==0) {
			while(true) {
			System.out.println(bufferedReader.readLine());
			System.out.print(">>");
			}
			}else {
				while(true) {
				cw.receiveMessage(bufferedReader.readLine());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
