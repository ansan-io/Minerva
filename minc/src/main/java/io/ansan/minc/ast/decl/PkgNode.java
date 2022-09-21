package io.ansan.minc.ast.decl;

import io.ansan.minc.ast.INode;
import io.ansan.minc.token.Token;

public record PkgNode(Token ident, Token value) implements INode.IDeclNode {}
