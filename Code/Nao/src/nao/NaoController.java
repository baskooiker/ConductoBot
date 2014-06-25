package nao;

import com.aldebaran.proxy.ALBehaviorManagerProxy;

public class NaoController {

	static String NAOQI_IP;
	static int NAOQI_PORT;
	static private ALBehaviorManagerProxy localProxyBehavior;
	static private NaoProxyThread<ALBehaviorManagerProxy> proxyBehavior;
	public NaoController(String n, int port)
	{
		NAOQI_IP=n;
		NAOQI_PORT = port;
		//Connect with Nao
		localProxyBehavior = new ALBehaviorManagerProxy (NAOQI_IP, NAOQI_PORT);
		proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
				localProxyBehavior	
				);
	}
	
	public static void start()
	{
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "tell-time"); 
		System.out.print("Hello World");
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
