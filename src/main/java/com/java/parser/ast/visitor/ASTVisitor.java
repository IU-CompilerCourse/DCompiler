package com.java.parser.ast.visitor;

import com.java.parser.ast.node.ASTNode;
import com.java.parser.ast.node.AssignNode;
import com.java.parser.ast.node.BinaryOpNode;
import com.java.parser.ast.node.TokenLiteralNode;
import com.java.parser.ast.node.VarDeclNode;
import com.java.parser.ast.node.VarNode;

public interface ASTVisitor<R> {
    R visitAssignNode(AssignNode node);

    R visitVarDeclNode(VarDeclNode node);

    R visitLiteralNode(TokenLiteralNode node);

    R visitBinaryOpNode(BinaryOpNode node);

    R visitVarNode(VarNode node);
}
