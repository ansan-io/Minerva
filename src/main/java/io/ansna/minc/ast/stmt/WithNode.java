package io.ansna.minc.ast.stmt;

import io.ansna.minc.ast.INode.IStmtNode;
import io.ansna.minc.ast.IType;
import io.ansna.minc.token.Token;

public record WithNode(Token ident, Token name, IType pointer) implements IStmtNode {}
