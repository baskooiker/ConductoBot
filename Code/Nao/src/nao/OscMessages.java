package nao;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCMessage;

public class OscMessages {
 	com.illposed.osc.OSCPortOut sender;
 	byte[] IPadres;
 	OSCMessage msg;
 	
 	public OscMessages(byte[] ip){
 		IPadres = ip;
 	}
	
	public void sendMessage(String behavior){
		msg = new OSCMessage("/nao/"+behavior);

		try {
			sender = new com.illposed.osc.OSCPortOut(InetAddress.getByAddress(IPadres), 1234);
		} catch (SocketException | UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
				sender.send(msg);
			 } 
		catch (Exception e) {
				 System.out.println("Couldn't send");
			 }		
	}
	
	public void sendMessage(String behavior, byte[] ip){
		msg = new OSCMessage("/nao/"+behavior);
 		try {
			sender = new com.illposed.osc.OSCPortOut(InetAddress.getByAddress(ip), 1234);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
				sender.send(msg);
			 } 
		catch (Exception e) {
				 System.out.println("Couldn't send");
			 }		
	}
}
