package io.ansan.minc.ast.stmt;

import io.ansan.minc.ast.INode.IStmtNode;
import io.ansan.minc.token.Token;

public record DeferNode(Token ident, BlockNode nodes) implements IStmtNode {}

