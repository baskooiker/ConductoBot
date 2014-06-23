package nao;


import com.aldebaran.proxy.*;

public class Main
{
 static
 {
   System.loadLibrary("JNaoQi");

 }
 static String NAOQI_IP = "naomi.local";
 static int NAOQI_PORT = 9559;
 static private ALBehaviorManagerProxy localProxyBehavior;
 static private NaoProxyThread<ALBehaviorManagerProxy> proxyBehavior;

 
 public static void main(String[] args)
 {
		localProxyBehavior = new ALBehaviorManagerProxy (NAOQI_IP, NAOQI_PORT);
		proxyBehavior = new NaoProxyThread<ALBehaviorManagerProxy>(
			localProxyBehavior	
		);
	 
	 proxyBehavior.start();
	// localProxyBehavior = new ALBehaviorManagerProxy(NAOQI_IP, NAOQI_PORT);
	// localProxyBehavior.runBehavior("tell-time");
	proxyBehavior.queueMethod("runBehavior", "dances/gangnam-style"); 
	System.out.print("Hello World");
	 
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