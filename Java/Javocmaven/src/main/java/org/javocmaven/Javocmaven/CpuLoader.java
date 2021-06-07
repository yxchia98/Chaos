package org.javocmaven.Javocmaven;

import java.time.LocalDateTime;

public class CpuLoader extends Loader {
	int duration = 5;
	double utilization = 50;
	
	public CpuLoader(int duration, double utilization) {
		this.duration = duration;
		this.utilization = utilization;
	}
	
	public CpuLoader(String arguments[]) {
		if(arguments.length >= 2) {
			this.duration = Integer.parseInt(arguments[0]);
			this.utilization = Double.parseDouble(arguments[1]);
		}
		else if(arguments.length == 1) {
			this.duration = Integer.parseInt(arguments[0]);
		}
		else {
		}
	}
	
	public void load() {
		int numCores = Runtime.getRuntime().availableProcessors();
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(this.duration);
		for (int i = 0; i < numCores; i++) {
			new BusyThread("Thread" + Integer.toString(i+1), endtime, this.utilization).start();
		}
	}
}
