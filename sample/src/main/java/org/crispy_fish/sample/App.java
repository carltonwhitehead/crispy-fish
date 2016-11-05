package org.crispy_fish.sample;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;
import org.crispy_fish.sample.results.RawResultsCommand;

public class App {
    public static void main(String[] args) {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("sample")
                .withDefaultCommand(Help.class)
                .withCommand(Help.class);

        builder.withGroup("results")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, RawResultsCommand.class);

        Cli<Runnable> sampleParser = builder.build();
        sampleParser.parse(args).run();
    }
}
