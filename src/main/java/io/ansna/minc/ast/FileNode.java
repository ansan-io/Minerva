package io.ansna.minc.ast;

import java.nio.CharBuffer;
import java.nio.file.Path;
import java.util.List;

public final class FileNode implements INode {
    String name;
    Path path;
    List<INode> nodes;
    CharBuffer source;
    INode ast;
}
