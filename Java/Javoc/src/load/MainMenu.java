package load;

import java.util.ArrayList;

public class MainMenu {

	public static void main(String[] args) {
		int duration = 5;
		String loadType = "";
		ArrayList<String> parameters = new ArrayList<String>();
		double utilization = 50;
		for (String str : args) {
			if (str.equals("cpu") || str.equals("mem") || str.equals("disk") || str.equals("net")) {
				loadType += str;
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
		if (loadType.contains("cpu")) {
			System.out.println("Loading CPU for: " + duration + "s, Utilization: " + utilization);
			CpuLoader.loadCpu(duration, utilization);
		} 
		
		if (loadType.contains("mem")) {
			System.out.println("Loading Memory for: " + duration + "s, Utilization: " + utilization);
			MemoryLeaker.testMem(duration, utilization);
		}

		if (loadType.contains("disk")) {
			System.out.println("Loading Disk for: " + duration + "s, Utilization: " + utilization + "MB/s");
			DiskWriter.DiskLoad(duration, utilization);
		}

//		else if (loadType.contains("cpu") && loadType.contains("mem")) {
//			System.out.println("Loading CPU and Memory for: " + duration + "s, Utilization: " + utilization);
//			CpuLoader.loadCpu(duration, utilization);
//			MemoryLeaker.testMem(duration, utilization);
//		}

	}

}
