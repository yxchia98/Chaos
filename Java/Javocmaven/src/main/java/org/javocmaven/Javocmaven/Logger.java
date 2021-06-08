package org.javocmaven.Javocmaven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.TimerTask;

public class Logger extends TimerTask {
	public void run() {
		File disklog = new File("/");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String logtxt = "LOGGED ON " + ts + "\n";
		long totalspace = disklog.getTotalSpace();
		long freespace = disklog.getUsableSpace();
		long usedspace = totalspace - freespace;
		double usedpercent = (double) usedspace / totalspace * 100;
		com.sun.management.OperatingSystemMXBean oslog = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();

//		System.out.println("LOGGED ON " + ts);
		
		logtxt += "Platform: " + System.getProperty("os.name") + "\n";

//		System.out.println("CPU load: " + Math.round(oslog.getCpuLoad() * 100 ) + "%");
		if (Math.round(oslog.getCpuLoad() * 100) == -1.0) {
			logtxt += "CPU load: 0%\n";
		} else {
			logtxt += "CPU load: " + Math.round(oslog.getCpuLoad() * 100) + "%\n";
		}

//		System.out.println("Total Memory(MB): " + oslog.getTotalMemorySize() / Math.pow(2, 20));
//		System.out.println("Free Memory(MB): " + oslog.getFreeMemorySize() / Math.pow(2, 20));
//		System.out.println(
//				"Used Memory(MB): " + (oslog.getTotalMemorySize() - oslog.getFreeMemorySize()) / Math.pow(2, 20));
//		System.out.println("JVM Allocated Memory(MB): " + (Runtime.getRuntime().maxMemory()) / Math.pow(2, 20));
		logtxt += "Total Memory(MB): " + oslog.getTotalMemorySize() / Math.pow(2, 20) + "\n";
		logtxt += "Free Memory(MB): " + oslog.getFreeMemorySize() / Math.pow(2, 20) + "\n";
		logtxt += "Used Memory(MB): " + (oslog.getTotalMemorySize() - oslog.getFreeMemorySize()) / Math.pow(2, 20)
				+ "\n";
		logtxt += "JVM Allocated Memory(MB): " + (Runtime.getRuntime().maxMemory()) / Math.pow(2, 20) + "\n";

//		System.out.println("Total space(100%):" + Math.toIntExact((long) (totalspace / Math.pow(2, 20)))
//				+ "MB   Used space(" + Math.round(usedpercent) + "%):"
//				+ Math.toIntExact((long) (usedspace / Math.pow(2, 20))) + "MB");
		logtxt += "Total space(100%):" + Math.toIntExact((long) (totalspace / Math.pow(2, 20))) + "MB   Used space("
				+ Math.round(usedpercent) + "%):" + Math.toIntExact((long) (usedspace / Math.pow(2, 20))) + "MB\n\n";

		File javoclog = new File("javoc.log");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(javoclog, true);
			fos.write(logtxt.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
