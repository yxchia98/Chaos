package org.javocmaven.Javocmaven;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		String dir = Paths.get(MainMenu.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString();
		System.out.println(dir);
	}
}
