package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.Statement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReadStatement extends Statement {
    private final Token readType;
    private ReferenceTail dest;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitReadStatement(this);
    }
}
