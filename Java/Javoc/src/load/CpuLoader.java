package load;

import java.time.LocalDateTime;

public class CpuLoader {
	public static void main(String[] args) throws InterruptedException {
		int numCores = Runtime.getRuntime().availableProcessors();
		int duration = 10;
		if (args.length > 0) {
			try {
				duration = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println("Timeout Arguement " + args[0] + " must be a integer");
			}
		}
		System.out.println("Number of cpu cores:" + numCores);
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
		for (int i = 0; i < numCores; i++) {
			new BusyThread(Integer.toString(i+1), endtime).start();
		}
	}
}
