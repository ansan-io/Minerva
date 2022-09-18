package io.ansna.minc.ast.decl;

import io.ansna.minc.ast.INode.IDeclNode;
import io.ansna.minc.ast.decl.DefNode.FunctionDefinition;
import io.ansna.minc.token.Token;

import java.util.List;

public record InterfaceNode(Token ex, Token ident, Token name, List<Token> parents, List<FunctionDefinition> definitions) implements IDeclNode {}
