package nao;

public class NaoBehavior {
	
	OscMessages osc;
	
	public void start_explanation(){
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "tell-time"); 
		osc.sendMessage("tempo_slow");		
		System.out.print("Tempo_slow");
	}
	
	public void explanation_tempo_big()
	{
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "tell-time"); 
		System.out.print("Hello World");
	}
	
	public void explanation_tempo_small()
	{
		proxyBehavior.start();
		proxyBehavior.queueMethod("runBehavior", "tell-time"); 
		System.out.print("Hello World");
	}
	

}
