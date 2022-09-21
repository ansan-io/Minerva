package io.ansan.minc.ast.decl;

import io.ansan.minc.ast.INode;
import io.ansan.minc.token.Token;

import java.util.List;

public record UseNode(Token ident, String path, List<Token> tokens) implements INode.IDeclNode {

    @Override
    public String toString() {
        return "UseNode{" +
            "ident=" + ident +
            ", path='" + path + '\'' +
            ", tokens=" + tokens +
            '}';
    }
}
