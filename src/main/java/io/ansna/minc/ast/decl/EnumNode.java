package io.ansna.minc.ast.decl;

import io.ansna.minc.ast.INode.IDeclNode;
import io.ansna.minc.token.Token;

import java.util.List;

public record EnumNode(Token ex, Token ident, Token name, List<EnumMember> members) implements IDeclNode {

  public record EnumMember(Token ident, List<Token> types) { }
}
