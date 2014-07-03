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
	NaoController c;
	Date lastAction;
	boolean assignedController;
	boolean firstRun;
	
	public NaoOSCListener() {
		firstrun = true;
		r = new BufferedReader(new InputStreamReader(System.in));
		righthandListener = new OSCListener() {
			@Override
			public void acceptMessage(Date time, OSCMessage message){
				System.out.println("Message van Bas: "
						+ message.getArguments().toString());
				
				if (message.getArguments().toString().equals("[0]")) {
					c.runBehavior("nao_approving");
				}
				else if (message.getArguments().toString().equals("[1]")) {
					c.runBehavior("nao_disapproving");
				}
				else
					System.err.println("Unexpected message arrived at righthandlistener");
			}
		};

		lefthandListener = new OSCListener() {
			@Override
			public void acceptMessage(Date time, OSCMessage message) {
				System.out.println("Message van Bas: "
						+ message.getArguments().toString());

				if (message.getArguments().toString().equals("[0]")) {
					c.runBehavior("nao_leftarm");
				}
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

	public void startListening() {
		in.addListener(righthandAS, righthandListener);
		in.addListener(lefthandAS, lefthandListener);
		in.startListening();
		if (in.isListening()) {
			System.out.println("Listener running!");
		} else {
			System.err.println("No Listener running!");
		}

		try {
			while (!(r.readLine()).equals("quit")) {
				System.out.println("Receiving quit command, stop listening..");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void assignNaoController(NaoController n) { 
		//c.deepCopy(n);
		c=n; assignedController = true;
	}
}
