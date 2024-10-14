package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class TupleListNode extends ASTNode {
    private final Map<Token, ASTNode> namedNodes;
    private final List<ASTNode> allNodes;

    public TupleListNode(ASTNode node) {
        namedNodes = new HashMap<>();
        allNodes = new ArrayList<>();

        allNodes.add(node);
    }

    public TupleListNode(Token identifier, ASTNode node) {
        namedNodes = new HashMap<>();
        allNodes = new ArrayList<>();

        namedNodes.put(identifier, node);
        allNodes.add(node);
    }

    public void append(ASTNode node) {
        allNodes.add(node);
    }

    public void append(Token identifier, ASTNode node) {
        namedNodes.put(identifier, node);
        allNodes.add(node);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTupleList(this);
    }
}
