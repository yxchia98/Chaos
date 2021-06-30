package org.javocmaven.Javocmaven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.Date;

import com.opencsv.CSVWriter;

public class Test {
	public static void main(String[] args) {
//		Timer timer = new Timer();
//		timer.schedule(new Logger(), 0);
		String cpuload = " ";
		File disklog = new File("/");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date datetime = new Date(ts.getTime());
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
		String date = dateformat.format(datetime);
		String time = timeformat.format(datetime);
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
		double usedpercentmem = (double) usedmem / totalmem * 100;

		if (os.contains("Windows")) {
			try {
				cpuload = getCPU("Windows");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				cpuload = getCPU("Linux");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		List<String[]> csvData = new ArrayList<>();

		String[] csvLog = { os, cpuload, String.valueOf(Math.toIntExact((long) (totalmem / Math.pow(2, 20)))),
				String.valueOf(Math.toIntExact((long) (usedmem / Math.pow(2, 20)))), String.valueOf(usedpercentmem),
				String.valueOf(Math.toIntExact((long) (totalspace / Math.pow(2, 20)))),
				String.valueOf(Math.toIntExact((long) (usedspace / Math.pow(2, 20)))), String.valueOf(usedpercentdisk),
				date, time };
		if (new File("javoclog.csv").exists()) {
			// append to file
			try {
				csvData.add(csvLog);
				CSVWriter writer = new CSVWriter(new FileWriter("javoclog.csv", true));
				writer.writeAll(csvData, false);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				String[] header = { "Platform", "CPU Load(%)", "Total Memory(MB)", "Used Memory(MB)", "Used Memory(%)",
						"Total Space(MB)", "Used Space(MB)", "Used Space(%)", "Log Date", "Log Time" };
				csvData.add(header);
				csvData.add(csvLog);
				CSVWriter writer = new CSVWriter(new FileWriter("javoclog.csv"));
				writer.writeAll(csvData, false);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static String getCPU(String type) throws IOException {
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
