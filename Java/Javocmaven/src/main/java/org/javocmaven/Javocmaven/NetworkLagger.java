package org.javocmaven.Javocmaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkLagger extends Loader {

	int duration;
	double utilization;

	public NetworkLagger(int duration, double utilization) {
		this.duration = duration;
		this.utilization = utilization;
	}
	
	public NetworkLagger(String[] arguments) {
		if(arguments.length >= 2) {
			this.duration = Integer.parseInt(arguments[0]);
			this.utilization = Double.parseDouble(arguments[1]);
		}
		else if(arguments.length == 1) {
			this.duration = Integer.parseInt(arguments[0]);
		}
		else {
		}
	}

	public void load() {
		String operatingSystem = System.getProperty("os.name");
		if(operatingSystem.contains("Windows")) {
			System.out.println("Injecting network latency of " + this.utilization + "ms, for " + this.duration + "s (Windows machine)");
			this.loadWindows();
		}
		else if (operatingSystem.contains("Linux")) {
			System.out.println("Injecting network latency of " + this.utilization + "ms, for " + this.duration + "s (Linux machine)");
			this.loadLinux();
		}
		

	}

	private void loadWindows() {
		String currentdir = System.getProperty("user.dir");
		String dir = "cd " + currentdir + "\\clumsy-0.2-win64\\";
		String stop = "Stop-Process -Name \"clumsy\"";
		String lagtime = "Start-Sleep -s " + Integer.toString(this.duration);
		String arguments = "--filter `\"outbound`\" --lag on --lag-time " + this.utilization;
		System.out.println(dir);
		try {
			execCommand(new ProcessBuilder("powershell.exe", dir, "\n",
					"Start-Process -WindowStyle Hidden .\\clumsy.exe -ArgumentList \"\"" + arguments + "\"\"", "\n",
					lagtime, "\n", stop));
		} catch (IOException e) {
			System.out.println("Unable to execute command.");
			e.printStackTrace();
		}
	}

	private void loadLinux() {
		String startcommand = "tc qdisc add dev ens192 root netem delay " + this.utilization + "ms";
		String endcommand = "tc qdisc del dev ens192 root netem delay " + this.utilization + "ms";
		try {
			execCommand(new ProcessBuilder("bash", "-c", startcommand));
			Thread.sleep(duration * 1000);
			execCommand(new ProcessBuilder("bash", "-c", endcommand));
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

//	public static void induceLag(int duration, double latency) {
//		String currentdir = System.getProperty("user.dir");
//		String dir = "cd " + currentdir + "\\clumsy-0.2-win64\\";
//		String stop = "Stop-Process -Name \"clumsy\"";
//		String lagtime = "Start-Sleep -s " + Integer.toString(duration);
//		String arguments = "--filter `\"outbound`\" --lag on --lag-time " + latency ;
//		System.out.println(dir);
//		try {
//			execCommand(new ProcessBuilder("powershell.exe", dir, "\n", 
//					"Start-Process -WindowStyle Hidden .\\clumsy.exe -ArgumentList \"\"" + arguments + "\"\"", "\n", 
//					lagtime, "\n", stop));
//		} catch (IOException e) {
//			System.out.println("Unable to execute command.");
//			e.printStackTrace();
//		}
//	}

	private void execCommand(ProcessBuilder builder) throws IOException {
		builder.redirectErrorStream(true);
		Process p = builder.start();
		p.getOutputStream().close();
		String line;
		// Standard Output
		BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = stdout.readLine()) != null) {
			System.out.println(line);
		}
		stdout.close();
		// Standard Error
		BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		while ((line = stderr.readLine()) != null) {
			System.out.println(line);
		}
		stderr.close();
		p.destroy();
	}

}
