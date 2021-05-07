package load;

import java.util.ArrayList;
import java.lang.management.*;

public class MemoryLeakExample {
	public static void main(String[] args) throws InterruptedException {
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		System.out.println(os.getAvailableProcessors());
		System.out.println(os.getTotalMemorySize());
		System.out.println(os.getFreeMemorySize());
		System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().totalMemory() + " B.");
		System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Math.pow(2, 30) + " GB, " + (long)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " B.");
		System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().maxMemory() + " B.");
		
		double inputsize = 4096 * Math.pow(2, 20);
		System.out.println(inputsize);
		ArrayList<char[]> hog = new ArrayList<char[]>();
		Runtime.getRuntime().gc();
		while((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) < inputsize) {
			hog.add(new char[524288]);
		}
//		for(int i = 0; i < inputsize; i++) {
////			hog.add(new char[524288]);
//			hog.add(new char[500000]);
//		}
		System.out.println();
		System.out.println(os.getTotalMemorySize());
		System.out.println(os.getFreeMemorySize());
		System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().totalMemory() + " B.");
		System.out.println("Used memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Math.pow(2, 30) + " GB, " + (long)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " B.");
		System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / Math.pow(2, 30) + " GB, " + (long)Runtime.getRuntime().maxMemory() + " B." + "\n\n");
		Thread.sleep(10000);
		System.out.println("There is no memory leak in this program.");
	}
}