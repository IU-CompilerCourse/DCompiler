package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ASTListNode extends ASTNode {
    private final List<ASTNode> nodes;

    public ASTListNode() {
        nodes = new ArrayList<>();
    }

    public ASTListNode(ASTNode node) {
        nodes = new ArrayList<>();
        nodes.add(node);
    }

    public ASTListNode(ASTNode node, ASTListNode astListNode) {
        nodes = new ArrayList<>();
        nodes.add(node);
        nodes.addAll(astListNode.nodes);
    }

    public void append(ASTNode node) {
        nodes.add(node);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
