package io.ansna.minc.ast;

import io.ansna.minc.token.Token;

public class IdentNode implements INode {
  public final Token ident;

  public IdentNode(Token ident) {
    this.ident = ident;
  }
}
