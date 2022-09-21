package io.ansna.minc.ast.decl;

import io.ansna.minc.ast.IType;
import io.ansna.minc.ast.INode.IDeclNode;
import io.ansna.minc.token.Token;

import java.util.List;

public record StructNode(Token ex, Token ident, Token name, Token parent, Token start, List<StructFieldNode> fields, Token end) implements IDeclNode {

  public record StructFieldNode(Token ident, IType type) {
  }
}
