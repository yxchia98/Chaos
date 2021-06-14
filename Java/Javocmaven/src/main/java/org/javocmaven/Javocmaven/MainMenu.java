package org.javocmaven.Javocmaven;

import java.util.ArrayList;
import java.util.Timer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainMenu {

	private static void executeLoad(Loader loadObj) {
		loadObj.load();
	}

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption(Option.builder("cpu").desc("CPU Loader").hasArgs().build());
		options.addOption(Option.builder("mem").desc("Memory Leaker").hasArgs().build());
		options.addOption(Option.builder("disk").desc("Disk Hogger").hasArgs().build());
		options.addOption(Option.builder("netlag").desc("Network Latency Injector").hasArgs().build());
		options.addOption(Option.builder("netnoise").desc("Network Packet Duplicator").hasArgs().build());
		options.addOption(Option.builder("netdrop").desc("Network Packet Dropper").hasArgs().build());
		options.addOption(Option.builder("netlimit").desc("Network Traffic Throttler").hasArgs().build());
		options.addOption(Option.builder("reboot").desc("Reboot current machine").build());
		ArrayList<BusyThread> threadArray = new ArrayList<BusyThread>();

		Timer timer = new Timer();
		timer.schedule(new Logger(), 0, 5000);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			Option[] parsedoptions = cmd.getOptions();
			for (Option a : parsedoptions) {
				if (a.getOpt().equals("cpu")) {
					CpuLoader cpuloader = new CpuLoader(a.getValues());
//					executeLoad(new CpuLoader(a.getValues()));
					executeLoad(cpuloader);
					threadArray = cpuloader.getThreadArray();
				} else if (a.getOpt().equals("mem")) {
					executeLoad(new MemoryLeaker(a.getValues()));
				} else if (a.getOpt().equals("disk")) {
					executeLoad(new DiskWriter(a.getValues()));
				} else if (a.getOpt().equals("netlag")) {
					executeLoad(new NetworkLagger(a.getValues(), "lag"));
				} else if (a.getOpt().equals("netnoise")) {
					executeLoad(new NetworkLagger(a.getValues(), "noise"));
				} else if (a.getOpt().equals("netdrop")) {
					executeLoad(new NetworkLagger(a.getValues(), "drop"));
				} else if (a.getOpt().equals("netlimit")) {
					executeLoad(new NetworkLagger(a.getValues(), "throttle"));
				} else if (a.getOpt().equals("reboot")) {
					executeLoad(new MachineReboot(a.getValues()));
				} else {
					System.out.println("Not enough arguments entered.");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < threadArray.size(); i++) {
			try {
				threadArray.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			timer.schedule(new Logger(), 1000);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		timer.purge();

	}

//	public static void main(String[] args) {
//		int duration = 5;
//		ArrayList<String> loadType = new ArrayList<String>();
//		ArrayList<String> parameters = new ArrayList<String>();
//		double utilization = 50;
//		for (String str : args) {
//			if (str.equals("cpu") || str.equals("mem") || str.equals("disk") || str.equals("net")) {
//				loadType.add(str);
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
//		for (String type : loadType) {
//			if (type.equals("cpu")) {
//				executeLoad(new CpuLoader(duration, utilization));
//			} else if (type.equals("mem")) {
//				executeLoad(new MemoryLeaker(duration, utilization));
//
//			} else if (type.equals("disk")) {
//				executeLoad(new DiskWriter(duration, utilization));
//
//			} else if (type.equals("net")) {
//				executeLoad(new NetworkLagger(duration, utilization));
//
//			}
//		}
//
//	}
}
