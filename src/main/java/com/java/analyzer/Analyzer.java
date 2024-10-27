package com.java.analyzer;

import com.java.analyzer.visitor.ContextCheckerVisitor;
import com.java.analyzer.visitor.UsageChecksVisitor;
import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Analyzer {
    public static void main(String[] args) throws IOException {
        var program = """
                var factorial := func(n) is
                    if n = 0 or n = 1 then
                        return 1;
                    else
                        return n * factorial(n - 1);
                    end
                end;

                var factResult := factorial(5), z := 5, a := z;

                var funcVal := func(x) => funcVal(a) + 1;

                return 10;
                """;
        var p = Parser.makeAST(new Lexer(program));
        p.accept(new PrintVisitor(0));
        var errors = p.accept(new UsageChecksVisitor());
        for (var error: errors) {
            System.out.println(error.error());
        }
        var contextErrors = p.accept(new ContextCheckerVisitor());
        for (var error: contextErrors) {
            System.out.println(error.error());
        }
    }
}
