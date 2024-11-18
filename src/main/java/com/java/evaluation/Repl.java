package com.java.evaluation;

import com.java.evaluation.objects.EmptyObj;
import com.java.lexer.Lexer;
import com.java.parser.Parser;
import java.util.Scanner;

@SuppressWarnings("all")
public class Repl {
    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        var evaluator = new Evaluator(true);

        System.out.print("> ");
        var command = scan.nextLine();
        while (!command.equals("/exit")) {
            try {
                var lex = new Lexer(command);
                var ast = Parser.makeAST(lex);
                if (ast != null) {
                    var res = ast.accept(evaluator);
                    if (!(res instanceof EmptyObj)) {
                        System.out.println(res);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.print("> ");
            command = scan.nextLine();
        }
    }
}
