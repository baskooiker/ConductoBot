package nao;

/**
 * 
 */
import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.aldebaran.proxy.ALProxy;

/**
 * Special thread to execute NAO behaviours in parallel
 * @author wouterbulten
 *
 */
public class NaoProxyThread<P extends ALProxy> extends Thread {

	private class Action {
		public String method;
		public String arg;
		
		public Action(String method, String arg)
		{
			this.method = method;
			this.arg = arg;
		}
	}
	
	private P proxy;
	
	private Queue<Action> actions = new LinkedBlockingQueue<Action>();
	
	public NaoProxyThread(P proxy) 
	{		
		this.proxy = proxy;
	}
	
	/**
	 * Sart up the thread
	 */
	public void run()
	{
		while(!this.isInterrupted())
		{
			try 
			{
				Action a;
				
				if((a = this.actions.poll()) != null)
				{
					Method m;
					
					m = this.proxy.getClass().getMethod(a.method, String.class);
					m.invoke(this.proxy, a.arg);				
				}
				else
				{
					try{
						Thread.sleep(200);
					}
					catch(InterruptedException e)
					{
						System.out.println("Proxy Thread stopped.");
						return;
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Add a new method call to the queue
	 * @param method
	 * @param arg
	 */
	public void queueMethod(String method, String arg)
	{
		System.out.println("Queued method: "+arg);
		this.actions.add(new Action(method, arg));
	}
	
	public ALProxy getProxy()
	{
		return this.proxy;
	}
}
