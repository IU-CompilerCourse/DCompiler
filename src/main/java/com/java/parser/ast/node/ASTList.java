package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ASTList extends ASTNode {
    private final List<ASTNode> nodes;

    public ASTList() {
        nodes = new ArrayList<>();
    }

    public ASTList(ASTNode node) {
        nodes = new ArrayList<>();
        nodes.add(node);
    }

    public ASTList(ASTNode node, ASTList astList) {
        nodes = new ArrayList<>();
        nodes.add(node);
        nodes.addAll(astList.nodes);
    }

    public void append(ASTNode node) {
        nodes.add(node);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitList(this);
    }
}
