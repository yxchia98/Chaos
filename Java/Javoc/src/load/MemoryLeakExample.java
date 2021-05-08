package load;

import java.util.ArrayList;
import java.lang.management.*;

public class MemoryLeakExample {
	public static void main(String[] args) throws InterruptedException {
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//		System.out.println(os.getAvailableProcessors());
		System.out.println("Total Memory: " + os.getTotalMemorySize() / Math.pow(2, 20));
		System.out.println("Free Memory: " + os.getFreeMemorySize() / Math.pow(2, 20));
		System.out.println("Used Memory: " + (os.getTotalMemorySize() - os.getFreeMemorySize()) / Math.pow(2, 20));
//		System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().totalMemory() + " B.");
//		System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Math.pow(2, 30) + " GB, " + (long)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " B.");
//		System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().maxMemory() + " B.");
		
		double targetUtilization = 80;
		double targetMemory = targetUtilization / 100 * os.getTotalMemorySize();
		System.out.println(targetMemory / Math.pow(2, 20));
		ArrayList<char[]> hog = new ArrayList<char[]>();
		Runtime.getRuntime().gc();
		while((os.getTotalMemorySize() - os.getFreeMemorySize()) < targetMemory) {
			hog.add(new char[524288]);
		}
		System.out.println();
		System.out.println("Total Memory: " + os.getTotalMemorySize() / Math.pow(2, 20));
		System.out.println("Free Memory: " + os.getFreeMemorySize() / Math.pow(2, 20));
		System.out.println("Used Memory: " + (os.getTotalMemorySize() - os.getFreeMemorySize()) / Math.pow(2, 20));
//		System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().totalMemory() + " B.");
//		System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Math.pow(2, 30) + " GB, " + (long)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " B.");
//		System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().maxMemory() + " B." + "\n\n");
		Thread.sleep(10000);
		System.out.println("There is no memory leak in this program.");
	}
}