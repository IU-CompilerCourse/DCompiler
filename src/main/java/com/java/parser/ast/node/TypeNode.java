package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.node.type.ConstructionType;
import com.java.parser.ast.visitor.ASTVisitor;

public class TypeNode extends ASTNode {
    private final Token type;
    private final ConstructionType constructionType;

    public TypeNode(Token type) {
        this.type = type;
        this.constructionType = null;
    }

    public TypeNode(ConstructionType type) {
        this.type = null;
        this.constructionType = type;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
