package load;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;

public class DiskWriter {

	public static void main(String[] args) {
		int duration = 10;
		int writeInstance = (int) (1024 * Math.pow(2, 20));
		byte bytes[] = new byte[writeInstance];
		LocalDateTime endtime = LocalDateTime.now().plusSeconds(duration);
		File myObj = new File("hogger.jpg");
//		try {
//		    System.out.println("Working Directory = " + System.getProperty("user.dir"));
//			if (myObj.createNewFile()) {
//				System.out.println("File created: " + myObj.getName());
//			} else {
//				System.out.println("File already exists.");
//			}
////			FileWriter myObjWriter = new FileWriter(myObj, true);
//			FileOutputStream outputStream = new FileOutputStream(myObj);
//			
//			while(LocalDateTime.now().isBefore(endtime)) {
//				outputStream.write(bytes);;
//			}
//			outputStream.close();
//		} catch (IOException e) {
//			System.out.println("An error occurred.");
//			e.printStackTrace();
//		}
//		myObj.delete();
		
		try (RandomAccessFile file = new RandomAccessFile(myObj, "rws")) {
			while(LocalDateTime.now().isBefore(endtime)) {
				file.seek(0);
				file.write(bytes);
				file.read(bytes);
			}
			file.close();
		}catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		myObj.deleteOnExit();
	}
}
