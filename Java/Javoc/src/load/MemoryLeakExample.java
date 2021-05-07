package load;

import java.util.Vector;

public class MemoryLeakExample {
	public static void main(String[] args) throws InterruptedException {
		double totalmemory = Runtime.getRuntime().totalMemory();
		double availablememory = totalmemory - Runtime.getRuntime().freeMemory();
		double maxmemory = Runtime.getRuntime().maxMemory();
		System.out.println("Total memory: " + totalmemory / Math.pow(10, 9));
		System.out.println("Available memory: " + availablememory / Math.pow(10, 9));
		System.out.println("Max memory: " + maxmemory / Math.pow(10, 9) + "\n\n");

		Vector v1 = new Vector(1000000000);
		totalmemory = Runtime.getRuntime().totalMemory();
		availablememory = totalmemory - Runtime.getRuntime().freeMemory();
		maxmemory = Runtime.getRuntime().maxMemory();
		System.out.println("Total memory: " + totalmemory / Math.pow(10, 9));
		System.out.println("Available memory: " + availablememory / Math.pow(10, 9));
		System.out.println("Max memory: " + maxmemory / Math.pow(10, 9) + "\n\n");
		Thread.sleep(10000);
		System.out.println("There is no memory leak in this program.");
	}
}