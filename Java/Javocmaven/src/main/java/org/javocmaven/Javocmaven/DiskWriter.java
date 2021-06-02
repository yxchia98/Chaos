package org.javocmaven.Javocmaven;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskWriter extends Loader {

	int duration = 5;
	double utilization = 20;

	public DiskWriter(int duration, double utilization) {
		this.duration = duration;
		this.utilization = utilization;
	}

	public DiskWriter(String arguments[]) {
		if (arguments.length >= 2) {
			this.duration = Integer.parseInt(arguments[0]);
			this.utilization = Double.parseDouble(arguments[1]);
		} else if (arguments.length == 1) {
			this.duration = Integer.parseInt(arguments[0]);
		} else {
		}
	}

	public void load() {
		System.out.println("Utilizing " + (int) this.utilization + "% of current partition.");

		File diskpartition = new File("/");
		long totalspace = diskpartition.getTotalSpace();
		long freespace = diskpartition.getUsableSpace();
		long usedspace = totalspace - freespace;
		double usedpercent = (double) usedspace / totalspace * 100; 
		double targetspace = this.utilization / 100 * totalspace;

		File myObj = new File("hogger.txt");
		myObj.deleteOnExit();

		System.out.println("Total space(100%):" + Math.toIntExact((long) (totalspace / Math.pow(2, 20))) + "MB   Used space(" + Math.round(usedpercent) + "%):"
				+ Math.toIntExact((long) (usedspace / Math.pow(2, 20))) + "MB   Target space(" + Math.round(this.utilization) + "%):"
				+ Math.toIntExact((long) (targetspace / Math.pow(2, 20))) + "MB.");

		try (RandomAccessFile file = new RandomAccessFile(myObj, "rws")) {
			if ((targetspace - usedspace) > 0) {
				file.setLength((long) targetspace - usedspace);
				System.out.println("Injected hogger.txt of " + Math.round(file.length() / Math.pow(2, 20)) + "MB");
				file.close();
				totalspace = diskpartition.getTotalSpace();
				freespace = diskpartition.getUsableSpace();
				usedspace = totalspace - freespace;
				usedpercent = (double) usedspace / totalspace * 100;
			} else {
				System.out.println("Already utilizing more than specified.");
			}
			Thread.sleep(this.duration * 1000);
			myObj.delete();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	public void load() {
//		System.out.println("Writing to Disk, duration: " + this.duration + "s, utilization: " + this.utilization + "MB/s");
//		LocalDateTime endtime = LocalDateTime.now().plusSeconds(this.duration);
//		File myObj = new File("hogger.txt");
//		char[] chars = new char[(int) ((1048576 * this.utilization) - 2)];
//		Arrays.fill(chars, 'f');
//		String megString = new String(chars) + "\n";
//
//		try (RandomAccessFile file = new RandomAccessFile(myObj, "rws")) {
//			RandomAccessFile readFile = new RandomAccessFile(myObj, "rws");
//			file.seek(0);
//			readFile.seek(0);
//			while (LocalDateTime.now().isBefore(endtime)) {
//				if ((System.currentTimeMillis() % 1000) == 0) {
//					file.writeBytes(megString);
////					System.out.println("writing " + megString.getBytes().length + "bytes");
//					Thread.sleep(1);
////					readFile.readLine();
//				}
//			}
//			readFile.close();
//			file.close();
//		} catch (IOException e) {
//			System.out.println("An error occurred.");
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		myObj.deleteOnExit();
//	}

//	public static void DiskLoad(int duration, double loadinMBs) {
//		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
//		File myObj = new File("hogger.txt");
//		char[] chars = new char[(int) ((1048576 * loadinMBs) - 2)];
//		Arrays.fill(chars, 'f');
//		String megString = new String(chars) + "\n";
//
//		try (RandomAccessFile file = new RandomAccessFile(myObj, "rws")) {
//			RandomAccessFile readFile = new RandomAccessFile(myObj, "rws");
//			file.seek(0);
//			readFile.seek(0);
//			while (LocalDateTime.now().isBefore(endtime)) {
//				if ((System.currentTimeMillis() % 1000) == 0) {
//					file.writeBytes(megString);
////					System.out.println("writing " + megString.getBytes().length + "bytes");
//					Thread.sleep(1);
////					readFile.readLine();
//				}
//			}
//			readFile.close();
//			file.close();
//		} catch (IOException e) {
//			System.out.println("An error occurred.");
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		myObj.deleteOnExit();
//	}
}
