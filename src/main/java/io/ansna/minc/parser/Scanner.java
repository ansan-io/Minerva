package io.ansna.minc.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Scanner {
  CharBuffer charBuffer;
  public Scanner(Path path) {
    try {
      var buf = Files.readAllBytes(path);
      ByteBuffer buffer = ByteBuffer.wrap(buf);
      charBuffer = StandardCharsets.UTF_8.decode(buffer);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
