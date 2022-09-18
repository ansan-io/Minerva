package io.ansna.minc.ast;

import io.ansna.minc.token.Token;

public class StringNode implements INode {
  Token ident;

  public StringNode(Token ident) {
    this.ident = ident;
  }
}
