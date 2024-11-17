package com.java;

import com.java.configuration.ApplicationConfiguration;
import com.java.evaluation.Evaluator;
import com.java.evaluation.Repl;
import com.java.lexer.Lexer;
import com.java.lexer.LexerOutput;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitorV2;
import com.java.semantic.Analyzer;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
@SuppressWarnings("all")
public class CompilerApplication implements CommandLineRunner {
    private static final String LINE_SEP = System.lineSeparator();
    private static final List<String> NO_VALUE_ARGS_TRIMMED =
        List.of("-r", "-l", "-p", "-a", "-i");
    private static final List<String> NO_VALUE_ARGS =
        List.of("--repl", "--lexer", "--parser", "--analyzer", "--interpreter");

    public static void main(String[] args) {
        SpringApplication.run(CompilerApplication.class, args);
    }

    public static String lexerFormatOutput(@NotNull LexerOutput output) {
        StringBuilder sb = new StringBuilder();

        sb.append("TOKENS").append(LINE_SEP.repeat(2));
        for (var token : output.tokens()) {
            sb.append(token).append(LINE_SEP);
        }

        sb.append(LINE_SEP).append("ERRORS").append(LINE_SEP.repeat(2));
        for (var error : output.lexingErrors()) {
            sb.append(String.format("Found error: '%s' at line %d", error.error(), error.atLine())).append(LINE_SEP);
        }

        return sb.toString();
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            runREPL();
        } else if (args.length == 1) {
            var arg1 = args[0];

            if (arg1.equals("-h") || arg1.equals("--help")) {
                printHelp();
                System.exit(0);
            }

            String sourceCode = Files.readString(new File("main.txt").toPath());

            proceed(args[0], sourceCode);
        } else {
            var map = collectArgsWithValues(args);

            if (map.containsKey("-r") || map.containsKey("--repl")) {
                if (map.keySet().size() > 1) {
                    System.out.println("In REPL mode there can be no actual arguments.");
                    System.exit(0);
                }

                runREPL();
                System.exit(0);
            }

            if (map.keySet().size() > 2) {
                System.out.println(
                    "There are a lot of arguments that are not allowable. Type '/help' to see the available interpreter options.");
            }

            String sourceCode = map.containsKey("--path") ? Files.readString(new File(map.get("--path")).toPath()) :
                Files.readString(new File(args[1]).toPath());

            proceed(args[0], sourceCode);
            System.exit(0);
        }
    }

    private void proceed(String arg, String sourceCode) {
        switch (arg) {
            case "-l", "--lexer" -> {
                try {
                    System.out.println(lexerFormatOutput(new Lexer(sourceCode).scanTokens()));
                } catch (Exception any) {
                    System.out.println(any.getMessage());
                }
                break;
            }
            case "-p", "--parser" -> {
                try {
                    Parser.makeAST(new Lexer(sourceCode)).accept(new PrintVisitorV2(0));
                } catch (Exception any) {
                    System.out.println(any.getMessage());
                }
                break;
            }
            case "-a", "--analyzer" -> {
                try {
                    var analyzerOutput = new Analyzer().analyze(Parser.makeAST(new Lexer(sourceCode)));
                    analyzerOutput.ast().accept(new PrintVisitorV2(0));
                    System.out.println(analyzerOutput);
                } catch (Exception any) {
                    System.out.println(any.getMessage());
                }
                break;
            }
            case "-i", "--interpreter" -> {
                try {
                    new Analyzer().analyze(Parser.makeAST(new Lexer(sourceCode))).ast()
                        .accept(new Evaluator(false));
                } catch (Exception any) {
                    System.out.println(any.getMessage());
                }
                break;
            }
            default -> {
                System.out.println("Something went wrong. Type '/help' to see the available interpreter options.");
            }
        }
    }

    private Map<String, String> collectArgsWithValues(String... args) {
        Map<String, String> map = new HashMap<>();
        List<String> arguments = new ArrayList<>(List.of(args));

        for (int i = 0; i < NO_VALUE_ARGS.size(); i++) {
            if (arguments.contains(NO_VALUE_ARGS.get(i)) && arguments.contains(NO_VALUE_ARGS_TRIMMED.get(i))) {
                System.out.println(
                    "There is duplicate argument: '" + NO_VALUE_ARGS_TRIMMED.get(i) + " | " + NO_VALUE_ARGS.get(i) +
                    "'; Type '/help' to see the available interpreter options.");
            }
        }

        for (int i = 0; i < args.length; i++) {
            var arg = args[i];

            if (arg.equals("--path")) {
                if (i + 1 >= args.length) {
                    System.out.println("There is no path provided to the interpreter.");
                    System.exit(0);
                }

                map.put(arg, args[i + 1]);
                i += 1;
                continue;
            }

            map.put(arg, null);
        }

        return map;
    }

    private void runREPL() {
        System.out.println(
            "Welcome to the D Interpreter in REPL mode! To exit, simply type '/exit'."
        );

        Repl.main(null);
    }

    private void printHelp() {
        System.out.println("""
             Welcome to Your CLI D Interpreter!
            \s
             Available Commands:
            \s
             -h / --help       : Get a quick overview of the interpreter and its capabilities.
            \s
             -r / --repl       : Dive into the interactive REPL mode. This is the default mode for our application, perfect for on-the-fly interpretation.
            \s
             -l / --lexer      : Execute only the Lexer part of the interpreter. Ideal for token enthusiasts!
            \s
             -p / --parser     : Run just the Parser component. For those who love syntax trees.
            \s
             -a / --analyzer   : Engage only the Analyzer segment. Great for semantic analysis aficionados.
            \s
             -i / --interpret  : Execute the entire interpreter on a specified file. The full experience!
            \s
            --path       : Specify the path to your source code file. Essential for all modes except REPL. Also, you can not to specify the argument name, just write the path after the necessary step
            """);
    }
}
