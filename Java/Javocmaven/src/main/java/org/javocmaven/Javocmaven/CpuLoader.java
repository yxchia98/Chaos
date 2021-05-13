package org.javocmaven.Javocmaven;

import java.time.LocalDateTime;

public class CpuLoader extends Loader {
	int duration;
	double utilization;
	
	public CpuLoader(int duration, double utilization) {
		this.duration = duration;
		this.utilization = utilization;
	}
	
	public void load() {
		int numCores = Runtime.getRuntime().availableProcessors();
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(this.duration);
		for (int i = 0; i < numCores; i++) {
			new BusyThread("Thread" + Integer.toString(i+1), endtime, this.utilization).start();
		}
	}
	
	public static void loadCpu(int duration, double utilization) {
		int numCores = Runtime.getRuntime().availableProcessors();
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
		for (int i = 0; i < numCores; i++) {
			new BusyThread("Thread" + Integer.toString(i+1), endtime, utilization).start();
		}
	}
}
