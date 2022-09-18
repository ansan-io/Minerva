package io.ansna.minc.parser;

import io.ansna.minc.Compiler;
import io.ansna.minc.ast.FileNode;
import io.ansna.minc.token.Position;
import io.ansna.minc.token.Token;
import io.ansna.minc.token.Token.Kind;
import static io.ansna.minc.token.Token.Kind.*;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private final Compiler compiler;
  private final FileNode node;
  int idx     = 0;
  int line    = 1;
  int column  = 1;

  public List<Token> tokenList;

  public Lexer(Compiler compiler, FileNode node) {
    this.compiler = compiler;
    this.node     = node;
    tokenList     = new ArrayList<>();

    while (lexToken().kind() != Kind.EOL){
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
        case '/' -> lexDiv(start);
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
        case ':' -> addToken(COLON, start, idx);
        //TODO(anthony): :=
        case '@' -> addToken(MACRO, start, idx);
        case '#' -> addToken(POUND, start, idx);
        case '.' -> addToken(PERIOD, start, idx);
        //TODO(anthony): ..
        case ',' -> addToken(COMMA, start, idx);
        case '\'' -> addToken(SINGLE_QUOTE, start, idx);
        case '"' -> addToken(DOUBLE_QUOTE, start, idx);
        default -> numbersAndIdents(start);
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

  private Token numbersAndIdents(int start) {
   if (Character.isDigit(peek())) return lexNum(start);
   if (Character.isAlphabetic(peek())) return  lexIdent(start);
   return null;
  }
  private Token lexIdent(int start) {
    var sb = new StringBuilder();
    if (Character.isAlphabetic(peek())) {
      sb.append(peek());
      advance();
      while (Character.isLetterOrDigit(peek())) {
        sb.append(peek());
        advance();
      }
    }
    return addToken(fromString(sb.toString()), start, idx);
  }

  private Token lexNum(int start) {
    var sb = new StringBuilder();
    if (match('0', 'x')) {
      sb.append('0');
      sb.append('x');
      advance(2);
      while (Character.isDigit(peek()) || (peek() >= 'A' && peek() <= 'F') || (peek() >= 'A' && peek() <= 'F')) {
        sb.append(peek());
        advance();
      }
      return addToken(sb.toString(), NUMBER, start, idx);
    }

    while (Character.isDigit(peek())) {
      sb.append(peek());
      advance();
    }
    return addToken(sb.toString(), NUMBER, start, idx);
  }

  //TODO(anthony): Fix to use match instead of this peek bullshit
  private Token lexNumber() {
    var sb = new StringBuilder();
    var start = idx;
    if (peek() == '0') {
      sb.append(peek());
      advance();
      if (peek() == 'x') {
       sb.append(peek());
       while (Character.isDigit(peek()) || (peek() >= 'A' && peek() <= 'F') || (peek() >= 'A' && peek() <= 'F')) {
        sb.append(peek());
        advance();
       }
       return addToken(sb.toString(), NUMBER, start, idx);
      }
    }
    while (Character.isDigit(peek())) {
      sb.append(peek());
      advance();
    }
    return addToken(sb.toString(), NUMBER, start, idx);
  }

  public Token lexDiv(int start) {
    var sb = new StringBuilder();
    if (match('/') && match('/')) {
      sb.append("//");
      advance(2);
      while (!match('\n')) {
        sb.append(peek());
        advance();
      }
      return addToken(sb.toString(), COMMENT, start, idx);
    } else {
      return addToken(DIVIDE, start, idx);
    }
  }

  public Token lexAnd(int start) {
    if (match('&') && match(1, '&')) {
      advance(2);
      return addToken(AND, start, idx);
    } else {
      return addToken(BIT_AND, start, idx);
    }
  }

  public Token lexColon(int start) {
    if (match(':') && match(1,'=')) {
      advance(2);
      return addToken(ASSIGN_INFER, start, idx);
    } else {
      return addToken(COLON, start, idx);
    }
  }

  public Kind fromString(String string) {
    for (var kind :Kind.values()) {
      if (kind.lexme.equals(string)) {
        return kind;
      }
    }
    return IDENT;
  }

  private void advance(int n) {
    this.idx = this.idx + n;
  }

  private Token addToken(String lexme, Kind kind, int start, int end) {
    return new Token(lexme, kind, new Position(start, end, this.line, this.column));
  }
  private Token addToken(Kind kind, int start, int end) {
    return new Token(kind, new Position(start, end, this.line, this.column));
  }

  private char peek() {
    return peek(0);
  }

  private char peek(int n) {
    return node.source.get(this.idx + n);
  }

  private void advance() {
    this.idx++;
    this.column++;
    if (peek() == '\n') {
      this.line++;
      this.column = 1;
    }
  }
  private boolean match(char c) {
    return match(0, c);
  }

  private boolean match(char... chars) {
    var index = 0;
    for (var c : chars) {
      if (peek(index) != c) return false;
      index++;
    }
    return true;
  }

  private boolean match(int i, char c) {
    return this.peek(i) == c;
  }
}
