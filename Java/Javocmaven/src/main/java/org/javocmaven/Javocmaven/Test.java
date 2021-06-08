package org.javocmaven.Javocmaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		String currentdir = "\\";
		try {
			currentdir = Paths.get(MainMenu.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().getParent().toString();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String dir = "cd " + currentdir + "\\clumsy-0.2-win64\\";
		String appPath = currentdir + "\\clumsy-0.2-win64\\clumsy.exe";
		String stop = "Stop-Process -Name 'clumsy'";
		String lagtime = "Start-Sleep -s " + Integer.toString(5);
		String arguments = "--filter \"\"ip.DstAddr >= 0.0.0.0 \"\"\" --duplicate on --duplicate-inbound on --duplicate-outbound off --duplicate-chance 100 --duplicate-count " + 50;
		String start = "Start-Process " + appPath + " -ArgumentList '" + arguments + "'";
		System.out.println(dir);
		try {
			execCommand(new ProcessBuilder("powershell.exe", dir, "\n",
					start, "\n",
					lagtime, "\n", stop));
		} catch (IOException e) {
			System.out.println("Unable to execute command.");
			e.printStackTrace();
		}
	}
	
	private static void execCommand(ProcessBuilder builder) throws IOException {
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
