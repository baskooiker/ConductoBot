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
		receiverIP = new byte[]{10, 0, 1, 8};
		NAOQI_IP=n;
		NAOQI_PORT = port;
		//Connect with Nao
		localProxyBehavior = new ALBehaviorManagerProxy (NAOQI_IP, NAOQI_PORT);
		proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
				localProxyBehavior	
				);

		
		osc = new OscMessages(receiverIP);
	}
	
	public void test(){
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "conductobot/tempo_big");
		
		
	}
	
	public void start(String behavior) throws InterruptedException
	{
		//proxyBehavior.wait();
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "conductobot/" +behavior); 
		
	}
	
	public void runBehaviors() throws InterruptedException{
		//start instructions
		if(!localProxyBehavior.isBehaviorRunning("conductobot/instructions")){
			osc.sendMessage("instructions");
			start("instructions"); 
			NaoProxyThread.sleep(1000);
			System.out.println("instructionsbehavior finished");
			
			localProxyBehavior.stopBehavior("conductobot/instructions");
			//check of the behavior geeindigd is na de stopbehavior
			if(!localProxyBehavior.isBehaviorRunning("conductobot/instructions")){
				System.out.println("1. instructionsbehavior is ended");
		}
		}
		else{//probeer nog een keer uit te zetten
			System.out.println("Instructions are still running!");
			localProxyBehavior.stopBehavior("conductobot/instructions");
			//check nog een keer of de behavior gestopt is
			if(!localProxyBehavior.isBehaviorRunning("conductobot/instructions")){
				System.out.println("2. instructionsbehavior is ended");
			}		
		}
		String[] runningbehaviors = localProxyBehavior.getRunningBehaviors();
		if(runningbehaviors.length != 0){
			System.out.println("Behaviors more than 1");
		}
		localProxyBehavior.stopAllBehaviors();
		if(runningbehaviors.length == 0){
			System.out.println("Behaviors succesfully ended");
		}
		
		
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
	
	public void runBehavior(String behavior){
		try {
			start(behavior);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osc.sendMessage(behavior);	
	}
	
	
	public void stop()
	{
		proxyBehavior.interrupt();
	}
// localProxyBehavior = new ALBehaviorManagerProxy(NAOQI_IP, NAOQI_PORT);
// localProxyBehavior.runBehavior("tell-time");
	//proxyBehavior.queueMethod("runBehavior", "tell-time"); 
// conductobot/ \ explanation_tempo_big
	//System.out.print("Hello World");

}
