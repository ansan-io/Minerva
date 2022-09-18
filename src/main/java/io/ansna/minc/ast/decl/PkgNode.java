package io.ansna.minc.ast.decl;

import io.ansna.minc.ast.INode.IDeclNode;
import io.ansna.minc.token.Token;

public record PkgNode(Token ident, Token value) implements IDeclNode {}
