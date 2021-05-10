package load;

public class MainMenu {

	public static void main(String[] args) {
		int duration = 5;
		String loadType = "mem";
		double utilization = 50;
		if (args.length > 0) {
			try {
				loadType = args[0];
			} catch (Exception e) {
				System.out.println("Invalid argument.");
			}
		} else {
			System.out.println("Not enough arguments.");
			System.exit(0);
		}

		switch (loadType) {
		case "cpu":
			try {
				duration = Integer.parseInt(args[1]);
				utilization = Double.parseDouble(args[2]);
			} catch (NumberFormatException e) {
				System.out.println("Timeout Arguement " + args[0] + " must be a integer");
			}
			System.out.println("Parsing duration: " + duration + ", Utilization: " + utilization);
			CpuLoader.loadCpu(duration, utilization);
			break;
		case "mem":
			try {
				duration = Integer.parseInt(args[1]);
				utilization = Double.parseDouble(args[2]);
			} catch (NumberFormatException e) {
				System.out.println("Timeout Arguement " + args[0] + " must be a integer");
			} catch (Exception e) {
				System.out.println("Not enough arguments.");
			}
			System.out.println("Parsing duration: " + duration + ", Utilization: " + utilization);
			MemoryLeaker.testMem(duration, utilization);
			break;
		default:
			System.out.println("Invalid arguments entered.");
			break;
		}

	}

}
