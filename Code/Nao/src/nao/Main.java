package nao;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import com.aldebaran.proxy.*;
import com.illposed.osc.*;

public class Main
{
 static
 {
   System.loadLibrary("JNaoQi");
 }
 static String NAOQI_IP = "naomi.local";//"naomi.local"; //127.1.1.1
 static int NAOQI_PORT = 9559;

 public static void main(String[] args) throws Exception {
	
		System.out.println("Starting Nao");
		NaoController nc = new NaoController(NAOQI_IP, NAOQI_PORT);
		NaoOSCListener ls = new NaoOSCListener();	
	
		NaoController.runBehaviors();
		//NaoController.test();
		
		System.out.println("Nao is running, type quit to stop.");
		
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		
		ls.startListening();
		
		while(!(r.readLine()).equals("quit")) ;
		
		System.out.println("Receiving quit command, stopping..");
		
		//Quit the controller
		NaoController.stop();
		System.out.println("Nao main thread stopped."); 
		
 	}
}