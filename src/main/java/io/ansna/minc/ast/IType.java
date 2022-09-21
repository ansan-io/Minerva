package io.ansna.minc.ast;

public interface IType {

  TypeKind getTypeKind();
  enum TypeKind {
    USER,
    BUILTIN,
    POINTER,
  }
}
