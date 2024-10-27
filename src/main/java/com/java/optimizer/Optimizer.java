package com.java.optimizer;

import com.java.lexer.Lexer;
import com.java.optimizer.visitor.UnreachableCodeVisitor;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Optimizer {
    public static void main(String[] args) throws IOException {
        var program = """
                var f := func(n) is
                    var arr := [1, 2, 3];
                    while i < 10 loop
                        return 10;
                        print i;
                    end
                    for i in arr loop
                        if i = 2 then
                            i := i + 1;
                            return 10;
                            i := 3;
                        end
                        i := 4;
                        return 12;
                        i := 5;
                    end
                    n := n + 1;
                    return 10;
                    return 20;
                    print n;
                end;
                """;
        var p = Parser.makeAST(new Lexer(program));
        p.accept(new PrintVisitor(0));
        p.accept(new UnreachableCodeVisitor());
        p.accept(new PrintVisitor(0));
    }
}
