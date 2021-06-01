package org.javocmaven.Javocmaven;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		File diskpartition = new File("/");
		long totalspace = diskpartition.getTotalSpace();
		long freespace = diskpartition.getUsableSpace();
		long usedspace = totalspace - freespace;
		double target = 20;
		double targetspace = target / 100 * totalspace;
		double utilization = (double) usedspace / totalspace * 100;
		
		System.out.println("Total space (Bytes): " + totalspace);
		System.out.println("Total space (MB): " + totalspace/Math.pow(2, 20));
		System.out.println("Total space (GB): " + totalspace/Math.pow(2, 30));
		System.out.println("Free space (Bytes): " + freespace);
		System.out.println("Free space (MB): " + freespace/Math.pow(2, 20));
		System.out.println("Free space (GB): " + freespace/Math.pow(2, 30));
		System.out.println("Used space (Bytes): " + usedspace + " (" + (int)utilization + "%)");
		
		File myObj = new File("hogger");
		myObj.deleteOnExit();
		RandomAccessFile file = new RandomAccessFile(myObj, "rws");
		if ((targetspace - usedspace) > 0) {
			file.setLength((long) targetspace - usedspace);
		}
		else {
			System.out.println("Already utilizing more than specified.");
		}

		
		System.out.println("--------------------------");
		totalspace = diskpartition.getTotalSpace();
		freespace = diskpartition.getUsableSpace();
		usedspace = totalspace - freespace;	
		utilization = (double) usedspace / totalspace * 100;
		System.out.println("Total space (Bytes): " + totalspace);
		System.out.println("Total space (MB): " + totalspace/Math.pow(2, 20));
		System.out.println("Total space (GB): " + totalspace/Math.pow(2, 30));
		System.out.println("Free space (Bytes): " + freespace);
		System.out.println("Free space (MB): " + freespace/Math.pow(2, 20));
		System.out.println("Free space (GB): " + freespace/Math.pow(2, 30));
		System.out.println("Used space (Bytes): " + usedspace + " (" + (int)utilization + "%)");
		
		Thread.sleep(5000);
		file.close();
		myObj.delete();
		
		System.out.println("--------------------------");
		totalspace = diskpartition.getTotalSpace();
		freespace = diskpartition.getUsableSpace();
		usedspace = totalspace - freespace;	
		utilization = (double) usedspace / totalspace * 100;
		System.out.println("Total space (Bytes): " + totalspace);
		System.out.println("Total space (MB): " + totalspace/Math.pow(2, 20));
		System.out.println("Total space (GB): " + totalspace/Math.pow(2, 30));
		System.out.println("Free space (Bytes): " + freespace);
		System.out.println("Free space (MB): " + freespace/Math.pow(2, 20));
		System.out.println("Free space (GB): " + freespace/Math.pow(2, 30));
		System.out.println("Used space (Bytes): " + usedspace + " (" + (int)utilization + "%)");

	}
}
