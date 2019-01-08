package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {

	public static void main(String[] args) {
		String line = "www.naver.com";
		try {
			InetAddress[] inetAddress = InetAddress.getAllByName(line);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
