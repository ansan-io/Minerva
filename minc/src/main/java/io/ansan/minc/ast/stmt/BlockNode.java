package io.ansan.minc.ast.stmt;

import io.ansan.minc.ast.INode;
import io.ansan.minc.token.Token;

import java.util.List;

public record BlockNode(Token start, List<INode.IStmtNode> statements, Token end) implements INode.IStmtNode {
}
