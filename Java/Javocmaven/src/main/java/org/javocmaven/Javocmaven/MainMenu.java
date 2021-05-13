package org.javocmaven.Javocmaven;

import java.util.ArrayList;

public class MainMenu {

	private static void executeLoad(Loader loadObj) {
		loadObj.load();
	}

	public static void main(String[] args) {
		int duration = 5;
		ArrayList<String> loadType = new ArrayList<String>();
		ArrayList<String> parameters = new ArrayList<String>();
		double utilization = 50;
		for (String str : args) {
			if (str.equals("cpu") || str.equals("mem") || str.equals("disk") || str.equals("net")) {
				loadType.add(str);
			} else {
				parameters.add(str);
			}
		}
		if (parameters.size() > 0) {
			if (parameters.size() == 1) {
				duration = Integer.parseInt(parameters.get(0));
			} else if (parameters.size() == 2) {
				duration = Integer.parseInt(parameters.get(0));
				utilization = Double.parseDouble(parameters.get(1));
			} else {
			}
		}

		for (String type : loadType) {
			if (type.equals("cpu")) {
				executeLoad(new CpuLoader(duration, utilization));
			} else if (type.equals("mem")) {
				executeLoad(new MemoryLeaker(duration, utilization));

			} else if (type.equals("disk")) {
				executeLoad(new DiskWriter(duration, utilization));

			} else if (type.equals("net")) {
				executeLoad(new NetworkLagger(duration, utilization));

			}
		}

	}
//	public static void main(String[] args) {
//		int duration = 5;
//		String loadType = "";
//		ArrayList<String> parameters = new ArrayList<String>();
//		double utilization = 50;
//		for (String str : args) {
//			if (str.equals("cpu") || str.equals("mem") || str.equals("disk") || str.equals("net")) {
//				loadType += str;
//			} else {
//				parameters.add(str);
//			}
//		}
//		if (parameters.size() > 0) {
//			if (parameters.size() == 1) {
//				duration = Integer.parseInt(parameters.get(0));
//			} else if (parameters.size() == 2) {
//				duration = Integer.parseInt(parameters.get(0));
//				utilization = Double.parseDouble(parameters.get(1));
//			} else {
//			}
//		}
//
//		if (loadType.equals("cpu")) {
//			System.out.println("Loading CPU for: " + duration + "s, Utilization: " + utilization);
//			CpuLoader.loadCpu(duration, utilization);
//		} 
//		else if (loadType.equals("mem")) {
//			System.out.println("Loading Memory for: " + duration + "s, Utilization: " + utilization);
//			MemoryLeaker.testMem(duration, utilization);
//		}
//
//		else if (loadType.equals("disk")) {
//			System.out.println("Loading Disk for: " + duration + "s, Utilization: " + utilization + "MB/s");
//			DiskWriter.DiskLoad(duration, utilization);
//		}
//		
//		else if (loadType.equals("net")) {
//			System.out.println("Inducing Network latency for: " + duration + "s, Utilization: " + utilization + "ms");
//			NetworkLagger.induceLag(duration, utilization);
//		}
//
//		else if (loadType.contains("cpu") && loadType.contains("mem")) {
//			System.out.println("Loading CPU and Memory for: " + duration + "s, Utilization: " + utilization);
//			CpuLoader.loadCpu(duration, utilization);
//			MemoryLeaker.testMem(duration, utilization);
//		}
//		
//		else if(loadType.contains("cpu") && loadType.contains("mem") && loadType.contains("disk")) {
//			System.out.println("Loading CPU, Memory and Disk for: " + duration + "s, Utilization: " + utilization);
//			CpuLoader.loadCpu(duration, utilization);
//			MemoryLeaker.testMem(duration, utilization);
//			DiskWriter.DiskLoad(duration, utilization);
//		}
//		
//		else if(loadType.contains("cpu") && loadType.contains("mem") && loadType.contains("disk")) {
//			System.out.println("Loading CPU, Memory, Disk and Network for: " + duration + "s, Utilization: " + utilization);
//			CpuLoader.loadCpu(duration, utilization);
//			MemoryLeaker.testMem(duration, utilization);
//			DiskWriter.DiskLoad(duration, utilization);
//			NetworkLagger.induceLag(duration, utilization);
//		}
//		
//		else {
//			System.out.println("Invalid arguments entered");
//		}
//
//	}
}
