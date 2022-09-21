package io.ansna.minc.ast;

public interface INode {

  interface IExprNode {}
  interface IStmtNode {}
  interface IDeclNode {}

  enum AstNode {

    DECLARATION_START(null),
    DEF_DECLARATION("DEF_DECLARATION"),
    ENUM_DECLARATION("ENUM_DECLARATION"),
    INTERFACE_DECLARATION("INTERFACE_DECLARATION"),
    PACKAGE_DECLARATION("PACKAGE_DECLARATION"),
    USE_DECLARATION("USE_DECLARATION"),
    DECLARATION_END(null),

    STATEMENT_START(null),
    STATEMENT_BLOCK("BLOCK_STATEMENT"),
    STATEMENT_WITH("WITH_STATEMENT"),
    STATEMENT_DEFER("DEFER_STATEMENT"),
    STATEMENT_END(null),

    ;

    public final String name;

    AstNode(String name) {
      this.name = name;
    }
  }

}
