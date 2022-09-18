package io.ansna.minc.ast.decl;

import io.ansna.minc.ast.INode.IDeclNode;
import io.ansna.minc.token.Token;

import java.util.List;

public record UseNode(Token ident, String path, List<Token> tokens) implements IDeclNode {

    @Override
    public String toString() {
        return "UseNode{" +
            "ident=" + ident +
            ", path='" + path + '\'' +
            ", tokens=" + tokens +
            '}';
    }
}
