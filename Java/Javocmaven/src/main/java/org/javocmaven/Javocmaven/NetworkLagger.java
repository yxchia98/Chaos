package org.javocmaven.Javocmaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class NetworkLagger extends Loader {

	int duration;
	double utilization;
	String type = "lag";

	public NetworkLagger(int duration, double utilization) {
		this.duration = duration;
		this.utilization = utilization;
	}

	public NetworkLagger(String[] arguments, String type) {
		if (type.equals("lag") || type.equals("noise") || type.equals("drop")) {
			this.type = type;
		}

		if (arguments.length >= 2) {
			this.duration = Integer.parseInt(arguments[0]);
			this.utilization = Double.parseDouble(arguments[1]);
		} else if (arguments.length == 1) {
			this.duration = Integer.parseInt(arguments[0]);
		} else {
		}
	}

	public void load() {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.contains("Windows")) {
			if (this.type.equals("lag")) {
				System.out.println("Injecting network latency of " + this.utilization + "ms, for " + this.duration
						+ "s (Windows machine)");
				this.lagWindows();
			} else if (this.type.equals("noise")) {
				System.out.println("Injecting network packet duplication of " + this.utilization + "%, for "
						+ this.duration + "s (Windows machine)");
				this.noiseWindows();
			} else if (this.type.equals("drop")) {
				System.out.println("Injecting network packet loss of " + this.utilization + "%, for " + this.duration
						+ "s (Windows machine)");
				this.dropWindows();
			}
		} else if (operatingSystem.contains("Linux")) {
			if (this.type.equals("lag")) {
				System.out.println("Injecting network latency of " + this.utilization + "ms, for " + this.duration
						+ "s (Linux machine)");
				this.lagLinux();
			} else if (this.type.equals("noise")) {
				System.out.println("Injecting network packet duplication of " + this.utilization + "%, for "
						+ this.duration + "s (Linux machine)");
				this.noiseLinux();
			} else if (this.type.equals("drop")) {
				System.out.println("Injecting network packet loss of " + this.utilization + "%, for " + this.duration
						+ "s (Linux machine)");
				this.dropLinux();
			}

		}

	}

	private void lagWindows() {
//		String currentdir = System.getProperty("user.dir");
		String currentdir = "\\";
		try {
			currentdir = Paths.get(MainMenu.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParent().toString();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String dir = "cd " + currentdir + "\\clumsy-0.2-win64\\";
		String appPath = currentdir + "\\clumsy-0.2-win64\\clumsy.exe";
		String stop = "Stop-Process -Name 'clumsy'";
		String lagtime = "Start-Sleep -s " + Integer.toString(this.duration);
		String arguments = "--filter \"\"ip.DstAddr >= 0.0.0.0 \"\"\" --lag on --lag-inbound off --lag-outbound on --lag-time " + this.utilization;
		String start = "Start-Process -WindowStyle Hidden " + appPath + " -ArgumentList '" + arguments + "'";
//		System.out.println(dir);
		try {
			execCommand(new ProcessBuilder("powershell.exe", dir, "\n", start, "\n", lagtime, "\n", stop));
		} catch (IOException e) {
			System.out.println("Unable to execute command.");
			e.printStackTrace();
		}
	}

	private void lagLinux() {
		String startcommand = "tc qdisc add dev ens192 root netem delay " + this.utilization + "ms";
		String endcommand = "tc qdisc del dev ens192 root netem delay " + this.utilization + "ms";
		try {
			execCommand(new ProcessBuilder("bash", "-c", startcommand));
			Thread.sleep(duration * 1000);
			execCommand(new ProcessBuilder("bash", "-c", endcommand));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void noiseWindows() {
		String currentdir = "\\";
		try {
			currentdir = Paths.get(MainMenu.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParent().toString();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String dir = "cd " + currentdir + "\\clumsy-0.2-win64\\";
		String appPath = currentdir + "\\clumsy-0.2-win64\\clumsy.exe";
		String stop = "Stop-Process -Name 'clumsy'";
		String lagtime = "Start-Sleep -s " + Integer.toString(this.duration);
		String arguments = "--filter \"\"ip.DstAddr >= 0.0.0.0 \"\"\" --duplicate on --duplicate-inbound off --duplicate-outbound on --duplicate-count 2 --duplicate-chance "
				+ this.utilization;
		String start = "Start-Process -WindowStyle Hidden " + appPath + " -ArgumentList '" + arguments + "'";
//		System.out.println(dir);
		try {
			execCommand(new ProcessBuilder("powershell.exe", dir, "\n", start, "\n", lagtime, "\n", stop));
		} catch (IOException e) {
			System.out.println("Unable to execute command.");
			e.printStackTrace();
		}
	}

	private void noiseLinux() {
		String startcommand = "tc qdisc add dev ens192 root netem duplicate " + this.utilization + "%";
		String endcommand = "tc qdisc del dev ens192 root netem duplicate " + this.utilization + "%";
		try {
			execCommand(new ProcessBuilder("bash", "-c", startcommand));
			Thread.sleep(duration * 1000);
			execCommand(new ProcessBuilder("bash", "-c", endcommand));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void dropWindows() {
		String currentdir = "\\";
		try {
			currentdir = Paths.get(MainMenu.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParent().toString();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String dir = "cd " + currentdir + "\\clumsy-0.2-win64\\";
		String appPath = currentdir + "\\clumsy-0.2-win64\\clumsy.exe";
		String stop = "Stop-Process -Name 'clumsy'";
		String lagtime = "Start-Sleep -s " + Integer.toString(this.duration);
		String arguments = "--filter \"\"ip.DstAddr >= 0.0.0.0 \"\"\" --drop on --drop-outbound on --drop-inbound off --drop-chance "
				+ this.utilization;
		String start = "Start-Process -WindowStyle Hidden " + appPath + " -ArgumentList '" + arguments + "'";
//		System.out.println(dir);
		try {
			execCommand(new ProcessBuilder("powershell.exe", dir, "\n", start, "\n", lagtime, "\n", stop));
		} catch (IOException e) {
			System.out.println("Unable to execute command.");
			e.printStackTrace();
		}
	}

	private void dropLinux() {
		String startcommand = "tc qdisc add dev ens192 root netem loss " + this.utilization + "%";
		String endcommand = "tc qdisc del dev ens192 root netem loss " + this.utilization + "%";
		try {
			execCommand(new ProcessBuilder("bash", "-c", startcommand));
			Thread.sleep(duration * 1000);
			execCommand(new ProcessBuilder("bash", "-c", endcommand));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

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
