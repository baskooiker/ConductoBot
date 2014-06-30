package nao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Date;

import com.illposed.osc.AddressSelector;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

public class NaoOSCListener {
	
	OSCListener righthandListener;
	OSCListener lefthandListener;
	OSCPortIn in;
	AddressSelector righthandAS;
	AddressSelector lefthandAS;
	BufferedReader r; 

	public NaoOSCListener(){
		r = new BufferedReader(new InputStreamReader(System.in));
		
		righthandListener = new OSCListener(){
			@Override
			public void acceptMessage(Date time, OSCMessage message) {
				// TODO Auto-generated method stub
				System.out.println("Message van Bas"+message.getArguments().toString());

			}
		};
		
		lefthandListener = new OSCListener(){
			@Override
			public void acceptMessage(Date time, OSCMessage message) {
				// TODO Auto-generated method stub
				System.out.println("Message van Bas"+message.getArguments().toString());

			}
		};
		
		
		try {
			in = new OSCPortIn(1338);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		righthandAS = new AddressSelector() {
			
			@Override
			public boolean matches(String messageAddress) {
				// TODO Auto-generated method stub
				System.out.println(messageAddress);
				return messageAddress.equals("/kinect/tempo");
			}
		};
		
		lefthandAS = new AddressSelector() {
			
			@Override
			public boolean matches(String messageAddress) {
				// TODO Auto-generated method stub
				System.out.println(messageAddress);
				return messageAddress.equals("/kinect/leftmoving");
			}
		};
	}
	
	public void startListening()
	{
		in.addListener(righthandAS,righthandListener);
		in.addListener(lefthandAS, lefthandListener);
		in.startListening();
		try {
			while(!(r.readLine()).equals("quit")){
				System.out.println("Receiving quit command, stop listening..");		
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
