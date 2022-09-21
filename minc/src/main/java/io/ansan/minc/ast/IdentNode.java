package io.ansan.minc.ast;

import io.ansan.minc.token.Token;

public class IdentNode implements INode {
  public final Token ident;

  public IdentNode(Token ident) {
    this.ident = ident;
  }
}
