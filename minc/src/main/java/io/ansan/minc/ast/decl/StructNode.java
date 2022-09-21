package io.ansan.minc.ast.decl;

import io.ansan.minc.ast.INode;
import io.ansan.minc.ast.IType;
import io.ansan.minc.token.Token;

import java.util.List;

public record StructNode(Token ex, Token ident, Token name, Token parent, Token start, List<StructFieldNode> fields, Token end) implements INode.IDeclNode {

  public record StructFieldNode(Token ident, IType type) {
  }
}
