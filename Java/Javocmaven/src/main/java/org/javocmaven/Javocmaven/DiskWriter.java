package org.javocmaven.Javocmaven;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DiskWriter {

//	public static void main(String[] args) {
//		int duration = 20;
////		int writeInstance = (int) (1024 * Math.pow(2, 20));
////		byte bytes[] = new byte[writeInstance];
//		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
//		File myObj = new File("hogger.txt");
//		char[] chars = new char[(1048576 * 500) - 2];
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
////					readFile.readLine();
//				}
//			}
//			file.close();
//		} catch (IOException e) {
//			System.out.println("An error occurred.");
//			e.printStackTrace();
//		}
//		myObj.deleteOnExit();
//
//	}
	
	public static void DiskLoad (int duration, double loadinMBs) {
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
		File myObj = new File("hogger.txt");
		char[] chars = new char[(int) ((1048576 * loadinMBs) - 2)];
		Arrays.fill(chars, 'f');
		String megString = new String(chars) + "\n";

		try (RandomAccessFile file = new RandomAccessFile(myObj, "rws")) {
			RandomAccessFile readFile = new RandomAccessFile(myObj, "rws");
			file.seek(0);
			readFile.seek(0);
			while (LocalDateTime.now().isBefore(endtime)) {
				if ((System.currentTimeMillis() % 1000) == 0) {
					file.writeBytes(megString);
//					System.out.println("writing " + megString.getBytes().length + "bytes");
					Thread.sleep(1);
//					readFile.readLine();
				}
			}
			readFile.close();
			file.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		myObj.deleteOnExit();
	}
}
