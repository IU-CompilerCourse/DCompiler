package com.java.parser.ast;

import com.java.parser.ast.node.ASTNode;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public final class ASTree {
    private final List<ASTNode> nodes;

    public ASTree() {
        nodes = new ArrayList<ASTNode>();
    }

    public void addNode(ASTNode node) {
        nodes.add(node);
    }

    public void addNodes(ASTNode... nodes) {
        this.nodes.addAll(List.of(nodes));
    }
}
