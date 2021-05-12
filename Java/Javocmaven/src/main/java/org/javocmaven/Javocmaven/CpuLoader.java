package org.javocmaven.Javocmaven;

import java.time.LocalDateTime;

public class CpuLoader {
	
	public static void loadCpu(int duration, double utilization) {
		int numCores = Runtime.getRuntime().availableProcessors();
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
		for (int i = 0; i < numCores; i++) {
			new BusyThread("Thread" + Integer.toString(i+1), endtime, utilization).start();
		}
	}
}
