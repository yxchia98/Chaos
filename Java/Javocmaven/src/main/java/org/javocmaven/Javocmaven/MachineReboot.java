package org.javocmaven.Javocmaven;

import java.io.IOException;

public class MachineReboot extends Loader{
	
	int duration = 0;
	
	public MachineReboot(String[] arguments, String durationType) {
		if (durationType.equals("seconds")) {
			if (arguments.length >= 1) {
				this.duration = Integer.parseInt(arguments[0]);
			}
		}
		else {
			if (arguments.length >= 1) {
				this.duration = Integer.parseInt(arguments[0]) * 60;
			}
		}

	}

	public void load() {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.contains("Windows")) {
			rebootWindows();
		}
		else {
			rebootLinux();
		}
	}
	
	private void rebootWindows() {
		String command = "Restart-Computer";
		try {
			System.out.println("Rebooting System in " + this.duration + "seconds. (Windows)");
			Thread.sleep(this.duration * 1000);
			execCommand(new ProcessBuilder("powershell.exe", command));
		} catch (IOException | InterruptedException e) {
			System.out.println("Unable to execute command.");
			e.printStackTrace();
		}
	}
	
	private void rebootLinux() {
		
		String command = "shutdown -r now";
		try {
			System.out.println("Rebooting System in " + this.duration + "seconds. (Linux)");
			Thread.sleep(this.duration * 1000);
			execCommand(new ProcessBuilder("bash", "-c", command));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
