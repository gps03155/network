package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

	public static void main(String[] args) {
		try {
	         InetAddress inetAddress = InetAddress.getLocalHost();
	         
	         String hostname = inetAddress.getHostName();
	         String hostaddress = inetAddress.getHostAddress();
	         
	         
	         System.out.println(hostname + " : " + hostaddress);
	         
	         byte[] addresses = inetAddress.getAddress();
	         for(byte address : addresses) {
//	            System.out.print(address + 256);
	            System.out.print(address & 0x000000ff);   // 비트연산
	            System.out.print(".");
	         }
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      }
	 	}

}
