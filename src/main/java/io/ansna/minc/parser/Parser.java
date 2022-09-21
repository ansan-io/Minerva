package io.ansna.minc.parser;

import io.ansna.minc.Compiler;
import io.ansna.minc.ast.*;
import io.ansna.minc.ast.INode.IStmtNode;
import io.ansna.minc.ast.decl.*;
import io.ansna.minc.ast.decl.DefNode.Parameter;
import io.ansna.minc.ast.decl.DefNode.FunctionDefinition;
import io.ansna.minc.ast.decl.EnumNode.EnumMember;
import io.ansna.minc.ast.decl.StructNode.StructFieldNode;
import io.ansna.minc.ast.stmt.BlockNode;
import io.ansna.minc.ast.stmt.DeferNode;
import io.ansna.minc.ast.stmt.WithNode;
import io.ansna.minc.token.Token;
import io.ansna.minc.token.Token.Kind;
import io.ansna.minc.util.Message;

import java.util.ArrayList;

import static io.ansna.minc.token.Token.Kind.*;

public final class Parser {
  public final FileNode node;
  private final Compiler compiler;
  private int idx;

  public Parser(Compiler compiler, FileNode node) {
    this.compiler = compiler;
    this.node     = node;
    this.idx      = 0;
  }

  //TODO(anita): implement
  private IStmtNode parse_stmt() {
    return null;
  }
  private PkgNode pkg_parse() {
    var ident = consume(PKG);
    var name  = consume(IDENT);
    return new PkgNode(ident, name);
  }

  private UseNode use_parse() {
    var ident       = consume(USE);
    var path        = new StringBuilder();
    var tokenstream = new ArrayList<Token>();
    for (;;) {
      path.append(consume(IDENT).lexme());
      if (match(PERIOD)) {
        advance();
      } else if (match(EOL)) {
        break;
      } else {
        log_error("Illegal token found in use expected a period or an EOL of a {" + peek().toString() + "} instead!");
      }
    }
    return  new UseNode(ident, path.toString(), tokenstream);
  }

  private StructNode struct_parse(boolean is_public) {
    Token ex          = null;
    if (is_public) ex = consume(EXPORT);
    var ident         = consume(STRUCT);
    var name          = consume(IDENT);
    Token parent      = null;

    if (match(COLON)) {
      consume(COLON);
      parent = consume(IDENT);
    }

    var start   = consume(OPEN_BRACE);
    var fields  = new ArrayList<StructFieldNode>();

    for(;;) {
      fields.add(struct_field_node());
      if (match(CLOSE_BRACE)) {
        break;
      } else if (match(EOL)) {
        consume(EOL);
      } else {
        log_error("illegal token at " + peek().toString());
      }
    }
    var end = consume(CLOSE_BRACE);
    return new StructNode(ex, ident, name, parent, start, fields, end);
  }

  private StructFieldNode struct_field_node() {
    var ident = consume(IDENT);
    var type = consume_type();

    return new StructFieldNode(ident, type);
  }

  private EnumNode enum_parse(boolean is_public) {
    Token ex = null;
    if (is_public) ex = consume(EXPORT);

    var ident         = consume(ENUM);
    var name          = consume(IDENT);
    var members       = new ArrayList<EnumNode.EnumMember>();

    consume(OPEN_BRACE);

    while (!match(CLOSE_BRACE)) {
      var member = enum_member();
      members.add(member);
      consume(EOL);
    }
    return new EnumNode(ex, ident, name, members);
  }

  private EnumMember enum_member() {
    var ident = consume(IDENT);
    var types = new ArrayList<IType>();
    if (match(OPEN_PARAN)) {
      consume(OPEN_PARAN);
      for (;;) {
        var type = consume_type();
        types.add(type);
        if (match(COMMA)) {
          consume(COMMA);
        }

        if (match(CLOSE_PARAN)) {
          consume(CLOSE_PARAN);
          break;
        }
      }
    } else if (!match(EOL)) {
      log_error("Illegal token " + peek().toString() + "expected an EOL");
    } else {
      log_error("How did we get here? Illegal token " + peek().toString() + " in enum member");
      System.exit(-1);
    }
    return new EnumMember(ident, types);
  }

  private DefNode def_parse(boolean is_public) {
   Token ex               = null;
   if (match(EXPORT)) ex  = consume(EXPORT);
   var func_def           = def_definition();
   var block              = block_parse();
   return new DefNode(ex, func_def, block);
  }

  private FunctionDefinition def_definition() {
    var ident = consume(DEF);
    var name = consume(IDENT);
    var parameters = new ArrayList<Parameter>();

    if (match(OPEN_PARAN)) {
      consume(OPEN_PARAN);
      for (;;) {
        var param = def_parameter();
        parameters.add(param);
        if (match(CLOSE_PARAN)) {
          consume(CLOSE_PARAN);
          break;
        } else if (match(COMMA)) {
          consume(COMMA);
        } else {
          log_error("How did we get here? illegal token " + peek().toString() + " in parameter");
        }
      }
    }

    var return_type = consume_type();

    return new FunctionDefinition(ident, name, parameters, return_type);
  }

  private Parameter def_parameter() {
    var ident = consume(IDENT);
    consume(COLON);
    var type = consume_type();
    return new Parameter(ident, type);
  }

  private InterfaceNode interface_parse(boolean is_public) {
    Token ex = null;
    if (is_public) ex = consume(EXPORT);
    var ident         = consume(INTERFACE);
    var name          = consume(IDENT);
    var parents       = new ArrayList<Token>();
    var definitions   = new ArrayList<FunctionDefinition>();

    if (match(COLON)) {
      consume(COLON);
      for (;;) {
        var parent = consume(IDENT);
        parents.add(parent);
        if (match(COMMA)) {
          consume(COMMA);
        } else if (match(EOL)) {
         break;
        } else {
          log_error("Issue reading parent token " + peek().toString());
        }
      }
    } else if (match(OPEN_BRACE)) {
      consume(OPEN_BRACE);
      consume(EOL);

      while (!match(CLOSE_PARAN)) {
        var defin = def_definition();
        definitions.add(defin);
        consume(EOL);
      }
      consume(CLOSE_BRACE);
    }

    return new InterfaceNode(ex, ident, name, parents, definitions);
  }

  /** Statement Parser **/

  private WithNode with_parse() {
    var ident = consume(WITH);
    var name  = consume(IDENT);
    consume(COLON);
    var ptr   = consume_type();
    assert ptr != null;
    if (ptr.getTypeKind() != IType.TypeKind.POINTER) {
      log_error("With must return with a pointer type " + peek(-1).toString());
    }
    return new WithNode(ident, name, ptr);
  }

  private DeferNode defer_parse() {
    var ident = consume(DEFER);
    BlockNode blk_node = null;
    if (!match(OPEN_BRACE)) {
      //call node
    } else if (match(OPEN_BRACE)) {
      blk_node = block_parse();
    } else {
      log_error("illegal parse on defer");
    }
    return new DeferNode(ident, blk_node);
  }


  private BlockNode block_parse() {
    var start     = consume(OPEN_BRACE);
    var stmt_list = new ArrayList<IStmtNode>();
    while (match(CLOSE_BRACE)) {
      stmt_list.add(parse_stmt());
    }
    var end = consume(OPEN_BRACE);
    return new BlockNode(start, stmt_list, end);
  }

  private IType consume_type() {
    if (is_primitive()) {
      var current_tok = peek();
      advance();
      return new BuiltinType(current_tok);
    } else if (match(IDENT)) {
      return new UserType(consume(IDENT));
    } else if (match(CARROT)) {
      var marker  = consume(CARROT);
      var ident   = consume(IDENT);
      //Check to see if the pointer semantics are correct
      assert marker != null : "How did this becomes null?";
      var mark_pos  = marker.pos();

      assert ident != null : "How did this become null";
      var ident_pos = ident.pos();

      if ((ident_pos.offset_start() - mark_pos.offset_start()) != 1) {
        log_error("Pointer must be next to Type identifier");
      }
      return new PointerType(marker, ident);
    } else {
      log_error("No valid type found found " + peek().toString() + "instead");
    }
    return null;
  }

  private void advance() { advance(0); }

  private void advance(int n) { this.idx = this.idx + n; }

  private Token peek(int n) { return node.tokens.get(idx + n); }

  private Token peek() { return peek(0); }
  
  private boolean match(int n, Kind kind) { return peek(n).kind() == kind; }

  private boolean match(Kind kind) { return match(0, kind); }

  private Token consume(Kind kind) {
    if (match(kind)) return peek();
    log_error("Type mismatch on token " + peek().lexme() + " expected kind " + kind.lexme);
    return null;
  }

  private boolean is_primitive() {
    return peek().is_primitive();
  }

  private void log_error(String msg) {
    var base_msg = String.format("%sParse Error: %s%s\n", Message.Color.CYAN.regular, msg, Message.Color.RESET.regular);
    compiler.compiler_raw_message(base_msg);
    System.exit(-1);
  }
}
