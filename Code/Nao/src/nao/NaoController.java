package nao;

import com.aldebaran.proxy.ALBehaviorManagerProxy;

public class NaoController {

	static String NAOQI_IP;
	static int NAOQI_PORT;
	static private ALBehaviorManagerProxy localProxyBehavior;
	static private NaoProxyThread<ALBehaviorManagerProxy> proxyBehavior;
	OscMessages osc;
 	byte[] receiverIP;
	public NaoController(String n, int port)
	{
		receiverIP = new byte[]{10, 0, 1, 6};
		NAOQI_IP=n;
		NAOQI_PORT = port;
		//Connect with Nao
		localProxyBehavior = new ALBehaviorManagerProxy (NAOQI_IP, NAOQI_PORT);
		proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
				localProxyBehavior	
				);
		
		osc = new OscMessages(receiverIP);
	}
	
	
	public static void test(){
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "Conductobot/" + "explanation_hand_horizontal"); 
		
	}
	
	public static void start(String behavior)
	{
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "Conductobot/" +behavior); 
		
	}
	
	public void runBehaviors(){
		//start instructions
		// TODO : runBehavior("instructions"); 
		
		//start instruction tempo small
		runBehavior("tempo_big");
		
		runBehavior("tempo_small");
		
		runBehavior("explanation_hand_horizontal");
		
		runBehavior("explanation_hand_horizontal_back");
		

		
		
	}
	
	public void runBehavior(String behavior){
		start(behavior);
		osc.sendMessage(behavior);	
	}
	
	
	public static void stop()
	{
		proxyBehavior.interrupt();
	}
// localProxyBehavior = new ALBehaviorManagerProxy(NAOQI_IP, NAOQI_PORT);
// localProxyBehavior.runBehavior("tell-time");
	//proxyBehavior.queueMethod("runBehavior", "tell-time"); 
// conductobot/ \ explanation_tempo_big
	//System.out.print("Hello World");

}
