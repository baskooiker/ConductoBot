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
 static String NAOQI_IP = "naomi.local"; //127.1.1.1
 static int NAOQI_PORT = 9559;

 public static void main(String[] args) throws Exception {
		System.out.println("Starting Nao");
		NaoController nc = new NaoController(NAOQI_IP, NAOQI_PORT);
		NaoOSCListener ls = new NaoOSCListener();
		
		
		
		NaoController.runBehaviors();
		//NaoController.test();
		
		System.out.println("Nao is running, type quit to stop.");
		
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		
		while(!(r.readLine()).equals("quit"));
		
		System.out.println("Receiving quit command, stopping..");
		
		//Quit the controller
		NaoController.stop();
		System.out.println("Nao main thread stopped."); 
		
		//start listening
		//typ quit to stop listening
		ls.startListening();
				

		
		
		
	/*	
		OSCListener ls = new OSCListener(){

			@Override
			public void acceptMessage(Date time, OSCMessage message) {
				// TODO Auto-generated method stub
				System.out.println("Message van Bas"+message.getArguments().toString());
				
			}
		};
		OSCPortIn in = new OSCPortIn(1337);
		AddressSelector as = new AddressSelector() {
			
			@Override
			public boolean matches(String messageAddress) {
				// TODO Auto-generated method stub
				System.out.println(messageAddress);
				return messageAddress.equals("/kinect/leftmoving");
			}
		};
		in.addListener(as,ls);
		in.startListening();
		 while(true){
			 
			 
		 }
		 
	 */
	 /*
	 	com.illposed.osc.OSCPortOut sender = new com.illposed.osc.OSCPortOut(InetAddress.getByAddress(new byte[]{10, 0, 1, 6}), 1234);
		OSCMessage msg = new OSCMessage("/nao/whateve");
		 try {
			sender.send(msg);
		 } catch (Exception e) {
			 System.out.println("Couldn't send");
		 */
	 
		

 
 
 /*
 
public static void main(String[] args)
 {
	 
	 localProxyBehavior = new ALBehaviorManagerProxy (NAOQI_IP, NAOQI_PORT);
		proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
			localProxyBehavior	
		);
	 proxyBehavior.start();
	// localProxyBehavior = new ALBehaviorManagerProxy(NAOQI_IP, NAOQI_PORT);
	// localProxyBehavior.runBehavior("tell-time");
	proxyBehavior.queueMethod("runBehavior", "tell-time"); 
	// conductobot/ \ explanation_tempo_big
	System.out.print("Hello World");
	
	*/
	 
	 /*
  // TTS proxy, myRobotIP.local
  ALTextToSpeechProxy ttsProxy = new ALTextToSpeechProxy("127.0.0.1", 9559);
  

  // Talk
  ttsProxy.say("hello world");
  
  
  // Test motion
  ALMotionProxy motion = new ALMotionProxy(NAOQI_IP, NAOQI_PORT);
  motion.wakeUp();
  motion.s
*/
 }
}