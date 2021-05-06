package load;

import java.time.LocalDateTime;

public class CpuLoader {
	public static void main(String[] args) throws InterruptedException {
		int numCores = Runtime.getRuntime().availableProcessors();
		int duration = 10;
		System.out.println("Number of cpu cores:" + numCores);
		System.out.println("Current System time: " + System.currentTimeMillis());
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
		for (int i = 0; i < numCores; i++) {
			new BusyThread(Integer.toString(i+1), endtime).start();
		}
	}
}
