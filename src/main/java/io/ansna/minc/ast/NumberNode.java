package io.ansna.minc.ast;

import io.ansna.minc.token.Token;
import io.ansna.minc.token.Token.Kind;;

public class NumberNode {
  public final Token ident;

  public NumberNode(Token ident) {
    this.ident = ident;
  }

  public Kind posibleKind() {
    //TODO(anthony): add a kind hecker for int kinds
    return null;
  }
}
