package nao;

import com.aldebaran.proxy.ALBehaviorManagerProxy;

public class NaoController {

	private static NaoController instance;
	static String NAOQI_IP;
	static int NAOQI_PORT;
	static private ALBehaviorManagerProxy localProxyBehavior;
	static private NaoProxyThread<ALBehaviorManagerProxy> proxyBehavior;
	static OscMessages osc;
 	byte[] receiverIP;
 	
	public NaoController(String n, int port)
	{
		receiverIP = new byte[]{10, 0, 1, 9};
		NAOQI_IP=n;
		NAOQI_PORT = port;
		//Connect with Nao
		localProxyBehavior = new ALBehaviorManagerProxy (NAOQI_IP, NAOQI_PORT);
		proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
				localProxyBehavior	
				);

		
		osc = new OscMessages(receiverIP);
	}
	
	public static synchronized void setup(String ip, int port) throws Exception
	{
		if(NaoController.instance != null)
		{
			throw new Exception("NaoController already started!");
		}
		
		//Connect with Nao
		NaoController.instance = new NaoController(ip, port);
	}
	
	public void test() throws InterruptedException{
		String[] behaviors = {"instructions","tempo_small", "tempo_big","hand_horizontal", "hand_horizontal_back"};
		for(int i= 0; i< behaviors.length; i++ )
		{
			runBehavior(behaviors[i]);
			if(!localProxyBehavior.isBehaviorRunning(behaviors[i])){
				runBehavior(behaviors[i+1]);
				System.out.println("Dit behavior is nu:"+behaviors[i+1]);
			}
			else{
				localProxyBehavior.stopBehavior(behaviors[i]);
			}	
		}
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "conductobot/tempo_big");	
	}
	
	public static void start()
	{
		/*
		if(NaoController.behaviourThread != null)
		{
			throw new RuntimeException("NaoController already started!");
		}
		
		//NaoController.behaviourThread = new Thread(new Runnable() {	
	
			
			public void run()
			{
				NaoController.getInstance();
				*/try {
					NaoController.runBehaviors();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	//	});
		
		//NaoController.behaviourThread.start();
	}
	
	public static NaoController getInstance()
	{	
		if(NaoController.instance == null)
		{
			throw new RuntimeException("NaoController not started.");
		}
		
		return NaoController.instance;
	}
	
	public static void startBehavior(String behavior)
	{
		//proxyBehavior.wait();
		NaoController.proxyBehavior.queueMethod("runBehavior", "conductobot/" +behavior); 	
	}
	
		
	protected static void runBehaviors() throws InterruptedException {
		NaoController.proxyBehavior.start();
		
		
			//NaoController.runBehavior("instructions");
			osc.sendMessage("instructions");
			NaoController.proxyBehavior.queueMethod("runBehavior", "conductobot/instructions");
			//NaoController.behaviourFinished = true;
			//System.out.println("Ja behavior gedaan van introductie");
			
			
				osc.sendMessage("instructions");
				Thread.sleep(1000);
				runBehavior("tempo_small");
				Thread.sleep(1000);
				runBehavior("tempo_big");
				Thread.sleep(1000);
				runBehavior("small_without_say");
				Thread.sleep(1000);
				//osc.sendMessage("instructions");
				runBehavior("hand_horizontal");
				Thread.sleep(1000);
				runBehavior("hand_horizontal_back");
			
			Thread.sleep(2000);
			
		
		
		
		//start instructions
		//osc.sendMessage("instructions");
		//runBehavior("instructions");
		
		//proxyBehavior.interrupt();
		//proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
		//		localProxyBehavior	
		//		);
		
						
		//start instruction tempo small
		//osc.sendMessage("instructions");
		//runBehavior("tempo_small");
		
		
		//start instruction tempo big
		//osc.sendMessage("instructions");
		//runBehavior("tempo_big");
		
		//start instruction tempo more instruments
		//osc.sendMessage("instructions");
		//runBehavior("hand_horizontal");
		//runBehavior("hand_horizontal_back");
		
		//osc.sendMessage("end");
		
	}
	
	public static void runBehavior(String behavior){
		startBehavior(behavior);
		//osc.sendMessage(behavior);
	}
	
	
	public static void stop()
	{
		System.out.println("Stopping");
		osc.sendMessage("/end");
		proxyBehavior.interrupt();
		osc.closePort();
	}
// localProxyBehavior = new ALBehaviorManagerProxy(NAOQI_IP, NAOQI_PORT);
// localProxyBehavior.runBehavior("tell-time");
	//proxyBehavior.queueMethod("runBehavior", "tell-time"); 
// conductobot/ \ explanation_tempo_big
	//System.out.print("Hello World");
	public void deepCopy (NaoController n) {
		this.NAOQI_IP = n.NAOQI_IP;
		this.NAOQI_PORT = n.NAOQI_PORT;
		this.localProxyBehavior = n.localProxyBehavior;
		this.proxyBehavior = n.proxyBehavior;
		this.osc = n.osc;
		this.receiverIP = n.receiverIP;
	}
	
	public ALBehaviorManagerProxy getLocalProxy() { return localProxyBehavior;}
	

}
