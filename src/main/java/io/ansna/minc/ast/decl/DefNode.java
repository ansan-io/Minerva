package io.ansna.minc.ast.decl;

import io.ansna.minc.ast.INode.IDeclNode;
import io.ansna.minc.ast.stmt.BlockNode;
import io.ansna.minc.token.Token;

import java.util.List;

public record DefNode(Token ex, FunctionDefinition function_definition, BlockNode block) implements IDeclNode {

  public record FunctionDefinition(Token ident, Token name, List<Parameter> parameters, Token return_type) {
  }

  public record Parameter(Token ident, Token type) {}
}
