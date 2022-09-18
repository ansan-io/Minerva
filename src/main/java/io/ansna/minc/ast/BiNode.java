package io.ansna.minc.ast;

import io.ansna.minc.token.Token;

public class BiNode implements INode {
  public final Token token;
  public final INode left;
  public final INode right;

  public BiNode(INode left, Token token, INode right) {
    this.left   = left;
    this.token  = token;
    this.right  = right;
  }

  public final class AddNode extends BiNode {
    public AddNode(INode left, Token token, INode right) {
      super(left, token, right);
    }
  }

  public final class SubNode extends BiNode {
    public SubNode(INode left, Token token, INode right) {
      super(left, token, right);
    }
  }

  public final class DivNode extends BiNode {
    public DivNode(INode left, Token token, INode right) {
      super(left, token, right);
    }
  }

  public final class MulNode extends BiNode {
    public MulNode(INode left, Token token, INode right) {
      super(left, token, right);
    }
  }
}
