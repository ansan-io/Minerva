package io.ansan.minc.ast;

import io.ansan.minc.token.Token;
import io.ansan.minc.token.Token.Kind;;

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
