package com.java.parser.ast.visitor;

import com.java.parser.ast.node.AssignNode;
import com.java.parser.ast.node.BinaryOpNode;
import com.java.parser.ast.node.LiteralNode;
import com.java.parser.ast.node.VarDeclNode;
import com.java.parser.ast.node.VarNode;

public interface ASTVisitor<R> {
    R visitAssignNode(AssignNode node);

    R visitVarDeclNode(VarDeclNode node);

    R visitLiteralNode(LiteralNode node);

    R visitBinaryOpNode(BinaryOpNode node);

    R visitVarNode(VarNode node);
}
