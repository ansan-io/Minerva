package io.ansna.minc.ast;

import io.ansna.minc.token.Token;

public record UserType(Token ident) implements IType {

  @Override
  public TypeKind getTypeKind() {
    return TypeKind.USER;
  }
}
