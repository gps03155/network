package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);

			while (true) {
				System.out.print("> ");
				String input = sc.nextLine();
				
				if(input.equals("exit")) {
					break;
				}
				InetAddress[] inetAddress = InetAddress.getAllByName(input);
				
				for(int i=0;i<inetAddress.length;i++) {
					System.out.println(input + " : " + inetAddress[i].getHostAddress());
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
