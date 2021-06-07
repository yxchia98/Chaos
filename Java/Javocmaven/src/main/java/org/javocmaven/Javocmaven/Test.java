package org.javocmaven.Javocmaven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		File disklog = new File("/");
		long totalspace = disklog.getTotalSpace();
		long freespace = disklog.getUsableSpace();
		long usedspace = totalspace - freespace;
		double usedpercent = (double) usedspace / totalspace * 100;
		com.sun.management.OperatingSystemMXBean oslog = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		
//		System.out.println("Process load: " + oslog.getProcessCpuLoad());
		System.out.println("System load: " + oslog.getCpuLoad());
		
		System.out.println("Total Memory(MB): " + oslog.getTotalMemorySize() / Math.pow(2, 20));
		System.out.println("Free Memory(MB): " + oslog.getFreeMemorySize() / Math.pow(2, 20));
		System.out.println("Used Memory(MB): " + (oslog.getTotalMemorySize() - oslog.getFreeMemorySize()) / Math.pow(2, 20));
		System.out.println("JVM Allocated Memory(MB): "+ (Runtime.getRuntime().maxMemory()) / Math.pow(2, 20));
		

		System.out.println("Total space(100%):" + Math.toIntExact((long) (totalspace / Math.pow(2, 20))) + "MB   Used space(" + Math.round(usedpercent) + "%):"
				+ Math.toIntExact((long) (usedspace / Math.pow(2, 20))) + "MB");
		
		File javoclog = new File("javoc.log");
		FileOutputStream fos = new FileOutputStream(javoclog, true);
		fos.write("Helloz\n".getBytes());
//		RandomAccessFile raf = new RandomAccessFile(javoclog, "rws");
//		raf.seek(raf.length());
//		raf.write("Hello\n".getBytes());
	}
}
