package com.java.analyzer;

import com.java.analyzer.visitor.UsageChecksVisitor;
import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.parser.ast.node.real.Array;
import com.java.parser.ast.node.real.Expression;
import com.java.parser.ast.node.real.ExpressionsCommaList;
import com.java.parser.ast.node.real.IfStatement;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Analyzer {
    public static void main(String[] args) throws IOException {
        var program = """
                var i := 0, n := 10;
                while i <= n loop
                    print a;
                    var sum := 5;
                    var sum := 10;
                    print sum;
                end
                """;
        var p = Parser.makeAST(new Lexer(program));
        p.accept(new PrintVisitor(0));
        var errors = p.accept(new UsageChecksVisitor());
        for (var error: errors) {
            System.out.println(error.error() + ", at line " + error.atLine());
        }
    }
}
