package io.ansna.minc.lexer;

import io.ansna.minc.token.Position;
import io.ansna.minc.token.Token;
import static io.ansna.minc.token.Token.Kind.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private final CharBuffer buffer;
  int idx     = 0;
  int line    = 1;
  int column  = 1;

  public List<Token> tokenList;

  public Lexer(CharBuffer buffer) {
    this.buffer = buffer;
    tokenList = new ArrayList<>();

    while (lexToken().kind() != Token.Kind.EOL){
      this.advance();
    }
  }

  private Token lexToken() {
    var start = idx;
    return switch (peek()) {
      case '\0' -> addToken(EOF, start, idx);
      case '\n', '\r' -> addToken(EOL, start, idx);
      case ' ', '\t'  -> null;
      case '(' -> addToken(OPEN_PARAN,    start, idx);
      case ')' -> addToken(CLOSE_PARAN,   start, idx);
      case '[' -> addToken(OPEN_BRACKET,  start, idx);
      case ']' -> addToken(CLOSE_BRACKET, start, idx);
      case '}' -> addToken(OPEN_BRACE,    start, idx);
      case '{' -> addToken(CLOSE_BRACE,   start, idx);
      case '+' -> lexPlus(start);
      case '-' -> lexMinus(start);
      case '/' -> addToken(DIVIDE,    start, idx);
      //TODO(anthony): lex comment
      case '%' -> addToken(MODULO,    start, idx);
      case '*' -> addToken(MULTIPLY,  start, idx);
      case '<' -> lexLess(start);
      case '>' -> lexGreat(start);
      case '!' -> lexBang(start);
      case '=' -> lexEq(start);
      case '|' -> lexPipe(start);
      case '~' -> addToken(BIT_NOT, start, idx);
      case '&' -> lexAnd(start);
      case '^' -> addToken(CARROT, start, idx);
      case ':' -> lexColon(start);
      case '@' -> addToken(MACRO, start, idx);
      case '#' -> addToken(POUND, start, idx);
      case '.' -> lexDot(start);
      //TODO(anthony): ...
      case ',' -> addToken(COMMA, start, idx);
      case '\'' -> addToken(SINGLE_QUOTE, start, idx);
      case '"' -> addToken(DOUBLE_QUOTE, start, idx);



      default -> throw new IllegalStateException("Unexpected value: " + peek());
    };
  }

  private Token lexPlus(int start) {
    if (match('+')) {
      advance();
      return addToken(INCREMENT, start, idx);
    } else {
      return addToken(ADD, start, idx);
    }
  }

  private Token lexMinus(int start) {
    if (match('-')) {
      advance();
      return addToken(DECREMENT, start, idx);
    } else if (match('>')) {
      advance();
      return addToken(RIGHT_ARROW, start, idx);
    } else {
      return addToken(SUBTRACT, start, idx);
    }
  }

  private Token lexLess(int start) {
    if (match('=')) {
      advance();
      return addToken(LESS_THAN_EQ, start, idx);
    } else if (match('<')) {
      advance();
      return addToken(SHIFT_LEFT, start, idx);
    } else if (match('-')) {
      advance();
      return addToken(LEFT_ARROW, start, idx);
    } else {
      return addToken(LESS_THAN, start, idx);
    }
  }

  private Token lexGreat(int start) {
    if (match('=')) {
      advance();
      return addToken(GREATER_THAN_EQ, start, idx);
    } else if (match('>')) {
      advance();
      return addToken(SHIFT_LEFT, start, idx);
    } else {
      return addToken(LESS_THAN, start, idx);
    }
  }

  private Token lexPipe(int start) {
    if (match('|')) {
      advance();
      return addToken(OR, start, idx);
    } else {
      return addToken(BIT_OR, start, idx);
    }
  }

  private Token lexBang(int start) {
    if (match('=')) {
      advance();
      return addToken(NOT_EQ, start, idx);
    } else {
      return addToken(NOT, start, idx);
    }
  }

  private Token lexEq(int start) {
    if (match('=')) {
      advance();
      return addToken(EQUAL, start, idx);
    } else {
      return addToken(ASSIGN, start, idx);
    }
  }

  private Token lexAnd(int start) {
    if (match('&')) {
      advance();
      return addToken(AND, start, idx);
    } else {
      return addToken(BIT_AND, start, idx);
    }
  }

  private Token lexColon(int start) {
    if (match('=')) {
      advance();
      return addToken(ASSIGN_INFER, start, idx);
    } else {
      return addToken(COLON, start, idx);
    }
  }

  private Token lexDot(int start) {
    if (match('.') && match(2, '.')) {
      advance(2);
      return addToken(RANGE, start, idx);
    } else {
      return addToken(PERIOD, start, idx);
    }
  }


  private char peek() {
    return peek(0);
  }

  private char peek(int n) {
    return buffer.get(this.idx + n);
  }

  private void advance() {
    this.idx++;
    this.column++;
    if (peek() == '\n') {
      this.line++;
      this.column = 1;
    }
  }

  private void advance(int n) {
    this.idx = this.idx + n;
  }

  private Token addToken(Token.Kind kind, int start, int end) {
    return new Token(kind, new Position(start, end, this.line, this.column));
  }

  private boolean match(char c) {
    return match(1, c);
  }

  private boolean match(int n, char c) {
    return this.peek(n) == c;
  }
}
