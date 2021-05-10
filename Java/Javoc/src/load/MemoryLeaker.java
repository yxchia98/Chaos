package load;

import java.util.ArrayList;
import java.lang.management.*;

public class MemoryLeaker {
	
	public static void testMem(int duration, double targetUtilization) {
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		System.out.println("Total Memory: " + os.getTotalMemorySize() / Math.pow(2, 20));
		System.out.println("Free Memory: " + os.getFreeMemorySize() / Math.pow(2, 20));
		System.out.println("Used Memory: " + (os.getTotalMemorySize() - os.getFreeMemorySize()) / Math.pow(2, 20));
		System.out.println("JVM Allocated Memory: "+ (Runtime.getRuntime().maxMemory()) / Math.pow(2, 20));
		
		double targetMemory = targetUtilization / 100 * os.getTotalMemorySize();
		System.out.println("Target Memory usage: " + targetMemory / Math.pow(2, 20));
		ArrayList<char[]> hog = new ArrayList<char[]>();
		Runtime.getRuntime().gc();
		while((os.getTotalMemorySize() - os.getFreeMemorySize()) < targetMemory) {
			hog.add(new char[524288]);
		}
		System.out.println();
		System.out.println("Total Memory: " + os.getTotalMemorySize() / Math.pow(2, 20));
		System.out.println("Free Memory: " + os.getFreeMemorySize() / Math.pow(2, 20));
		System.out.println("Used Memory: " + (os.getTotalMemorySize() - os.getFreeMemorySize()) / Math.pow(2, 20));
		try {
			Thread.sleep(duration * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Error putting thread to sleep");
		}
		System.out.println("There is no memory leak in this program.");

	}
}