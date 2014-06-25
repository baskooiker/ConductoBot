package nao;

import com.aldebaran.proxy.ALBehaviorManagerProxy;

public class NaoController {

	static String NAOQI_IP;
	static int NAOQI_PORT;
	static private ALBehaviorManagerProxy localProxyBehavior;
	static private NaoProxyThread<ALBehaviorManagerProxy> proxyBehavior;
	static OscMessages osc;
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
		proxyBehavior.queueMethod("runBehavior", "conductobot/tempo_big"); 
		
	}
	
	public static void start(String behavior)
	{
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "conductobot/" +behavior); 	
	}
	
	public static void runBehaviors(){
		//start instructions
		runBehavior("instructions"); 
		
		//start instruction tempo small
		runBehavior("tempo_small");
		
		//start instruction tempo big
		runBehavior("tempo_big");
		
		//start instruction tempo more instruments
		runBehavior("explanation_hand_horizontal");
		runBehavior("explanation_hand_horizontal_back");
		
	}
	
	public static void runBehavior(String behavior){
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
