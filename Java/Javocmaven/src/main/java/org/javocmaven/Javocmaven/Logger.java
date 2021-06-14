package org.javocmaven.Javocmaven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		double usedpercentdisk = (double) usedspace / totalspace * 100;

		com.sun.management.OperatingSystemMXBean oslog = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		String os = System.getProperty("os.name");

		long totalmem = oslog.getTotalMemorySize();
		long freemem = oslog.getFreeMemorySize();
		long usedmem = totalmem - freemem;
		long jvmmem = Runtime.getRuntime().maxMemory();
		double usedpercentmem = (double) usedmem / totalmem * 100;

//		System.out.println("LOGGED ON " + ts);

		logtxt += "Platform: " + os + "\n";

		if (os.contains("Windows")) {
			try {
				logtxt += "CPU load: " + getCPU("Windows") + "\n";
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				logtxt += "CPU load: " + getCPU("Linux") + "\n";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logtxt += "Total Memory(100%): " + Math.toIntExact((long) (totalmem / Math.pow(2, 20))) + "MB\nUsed Memory("
				+ Math.round(usedpercentmem) + "%): " + Math.toIntExact((long) (usedmem / Math.pow(2, 20))) + "\n";
		
		logtxt += "JVM Allocated Memory: " + jvmmem / Math.pow(2, 20) + "MB\n";

		logtxt += "Total Space(100%): " + Math.toIntExact((long) (totalspace / Math.pow(2, 20))) + "MB\nUsed Space("
				+ Math.round(usedpercentdisk) + "%): " + Math.toIntExact((long) (usedspace / Math.pow(2, 20)))
				+ "MB\n\n";

		File javoclog = new File("javoc.log");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(javoclog, true);
			fos.write(logtxt.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getCPU(String type) throws IOException {
		String line = null;
		ProcessBuilder builder = null;
		if (type.equals("Windows")) {
			builder = new ProcessBuilder("powershell.exe",
					"Get-WmiObject -class win32_processor | Measure-Object -property LoadPercentage -Average | Select-Object -ExpandProperty Average");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			p.getOutputStream().close();
			// Standard Output
			BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				stdout.close();
				return line + "%";
			}
			stdout.close();
			// Standard Error
			BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = stderr.readLine()) != null) {
				stderr.close();
				return line;
			}
		} else {
			builder = new ProcessBuilder("bash", "-c",
					"top -bn1 | grep -Po \"[0-9.]*(?=( id,))\" | awk '{print 100 - $1\"%\"}'");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			p.getOutputStream().close();
			// Standard Output
			BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				stdout.close();
				return line;
			}
			stdout.close();
			// Standard Error
			BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = stderr.readLine()) != null) {
				stderr.close();
				return line;
			}
		}
		return line;
	}
}
