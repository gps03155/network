package chat.win;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private static List<Writer> listWriters = new ArrayList<Writer>();

	public ChatServerThread( Socket socket ) {
		this.socket = socket;
	}
	public ChatServerThread( Socket socket, List<Writer> listWriters ) {
		   this.socket = socket;
		   this.listWriters = listWriters;
		}
	
	@Override
	public void run() {
		try {

			//1. Remote Host Information
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
	    Log("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort());
		
	    //2. 스트림 얻기
	    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
		PrintWriter	printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );
		
		//3. 요청 처리 			
		while( true ) {
		   String request = bufferedReader.readLine();
		   if( request == null) {
			   doQuit(printWriter);
		      Log( "클라이언트로 부터 연결 끊김" );
		      break;
		   }
		   

		   // 4. 프로토콜 분석
		   
		   String[] tokens = request.split( ":" );
			
		   if( "join".equals( tokens[0] ) ) {

		      doJoin( tokens[1], printWriter );

		   } else if( "message".equals(tokens[0]) ) {
		      Log(tokens[1]);
		      doMessage( tokens[1] );

		   } else if( "quit".equals( tokens[0] ) ) {
		      
		      doQuit(printWriter);

		   } else {
		      ChatServer.Log( "에러:알수 없는 요청(" + tokens[0] + ")" );
		   }

		}
		}catch(Exception e) {
			Log("error : "+e);
			return;
		}
		
	}

	private void doQuit(  Writer writer ) {
		 
		removeWriter( writer );		
		String data = nickname + "님이 퇴장 하였습니다."; 
		  broadcast( data );
		  
		}


	private void doMessage(String message) {
		
		 synchronized( listWriters ) {
			    for( Writer writer : listWriters ) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(nickname+": " + message );
				printWriter.flush();
			      }

			   }

	}

	private void doJoin(String nickName, Writer writer) {  
		this.nickname = nickName;
		String data = nickName + "님이 참여하였습니다."; 
		broadcast( data );
		   /* writer pool에  저장 */ 
		addWriter(writer);
		// ack
		PrintWriter printWriter = (PrintWriter) writer;
		printWriter.println( "join:ok" );
		printWriter.flush();

	}
	
	private void addWriter( Writer writer ) {
		Log("저장");
		   synchronized( listWriters ) {
		      listWriters.add( writer );
		   }
		}
	private void removeWriter( Writer writer ) {
		synchronized( listWriters ) {
		      listWriters.remove(writer);
		   }
		}

	private void broadcast( String data ) {
		System.out.println("broad");
		   synchronized( listWriters ) {
		    for( Writer writer : listWriters ) {
			PrintWriter printWriter = (PrintWriter)writer;
			printWriter.println( data );
			printWriter.flush();
		      }

		   }

		}

	private void Log(String message) {
		System.out.println("[Client#" + Thread.currentThread().getId()  + "] " + message);
	}
}
