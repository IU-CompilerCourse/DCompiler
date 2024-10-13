package com.java.parser.ast.node;

import com.java.parser.ast.node.type.ReadType;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadNode extends ASTNode {
    private final ReadType readType;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
