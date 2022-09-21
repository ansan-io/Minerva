package io.ansan.minc.ast;

import io.ansan.minc.token.Token;

public class StringNode implements INode {
  Token ident;

  public StringNode(Token ident) {
    this.ident = ident;
  }
}
