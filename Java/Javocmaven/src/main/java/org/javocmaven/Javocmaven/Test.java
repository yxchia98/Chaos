package org.javocmaven.Javocmaven;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Test {
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption(Option.builder("cpu").hasArgs().build());
		options.addOption(Option.builder("mem").hasArgs().build());
		options.addOption(Option.builder("disk").hasArgs().build());
		options.addOption(Option.builder("net").hasArgs().build());

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			Option[] parsedoptions = cmd.getOptions();
			for (Option a : parsedoptions) {
				String[] values = a.getValues();
				System.out.println(a.getOpt() + "\tvalue1: " + values[0] + "\tvalue2: " + values[1]);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
