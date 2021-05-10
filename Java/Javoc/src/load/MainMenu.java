package load;

import java.util.ArrayList;

public class MainMenu {

	public static void main(String[] args) {
		int duration = 5;
		String loadType = "";
		ArrayList<String> parameters = new ArrayList<String>();
		double utilization = 50;
		for (String str : args) {
			if(str.equals("cpu") || str.equals("mem")) {
				loadType += str;
			}
			else {
				parameters.add(str);
			}
		}
		if (parameters.size() > 0) {
			if(parameters.size() == 1) {
				duration = Integer.parseInt(parameters.get(0));
			}
			else if (parameters.size() == 2) {
				duration = Integer.parseInt(parameters.get(0));
				utilization = Double.parseDouble(parameters.get(1));
			}
			else {
			}
		}
//		if (args.length > 0) {
//			try {
//				duration = Integer.parseInt(args[1]);
//				utilization = Double.parseDouble(args[2]);
//			} catch (NumberFormatException e) {
//				System.out.println("2nd and 3rd arguments must be a number format.");
//			} catch (Exception e) {
//				System.out.println("Arguments entered");
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Not enough arguments.");
//			System.exit(0);
//		}

		switch (loadType) {
		
		case "cpu":
			System.out.println("Loading CPU for: " + duration + "s, Utilization: " + utilization);
			CpuLoader.loadCpu(duration, utilization);
			break;
			
		case "mem":
			System.out.println("Loading Memory for: " + duration + "s, Utilization: " + utilization);
			MemoryLeaker.testMem(duration, utilization);
			break;
			
		case "cpumem":
			System.out.println("Loading CPU and Memory for: " + duration + "s, Utilization: " + utilization);
			CpuLoader.loadCpu(duration, utilization);
			MemoryLeaker.testMem(duration, utilization);
			break;
			
		case "memcpu":
			System.out.println("Loading CPU and Memory for: " + duration + "s, Utilization: " + utilization);
			CpuLoader.loadCpu(duration, utilization);
			MemoryLeaker.testMem(duration, utilization);
			break;
			
		default:
			System.out.println("Invalid arguments entered.");
			break;
		}

	}

}
