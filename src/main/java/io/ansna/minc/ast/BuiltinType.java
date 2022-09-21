package io.ansna.minc.ast;

import io.ansna.minc.token.Token;

public record BuiltinType(Token ident) implements IType {

  @Override
  public TypeKind getTypeKind() {
    return TypeKind.BUILTIN;
  }
}
