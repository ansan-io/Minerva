package io.ansan.minc.ast.decl;

import io.ansan.minc.ast.INode;
import io.ansan.minc.ast.IType;
import io.ansan.minc.ast.stmt.BlockNode;
import io.ansan.minc.token.Token;

import java.util.List;

public record DefNode(Token ex, FunctionDefinition function_definition, BlockNode block) implements INode.IDeclNode {

  public record FunctionDefinition(Token ident, Token name, List<Parameter> parameters, IType return_type) {
  }

  public record Parameter(Token ident, IType type) {}
}
