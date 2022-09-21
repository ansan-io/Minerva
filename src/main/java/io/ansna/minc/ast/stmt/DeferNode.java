package io.ansna.minc.ast.stmt;

import io.ansna.minc.ast.INode.IStmtNode;
import io.ansna.minc.token.Token;

import java.util.List;

public record DeferNode(Token ident, BlockNode nodes) implements IStmtNode {}

