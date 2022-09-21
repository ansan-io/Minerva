package io.ansna.minc.util;

public final class Message {

  public String message;
  private Color color;

  public class MessageBuilder {
    private String message;
    private Color color;
    private String reset;

    public MessageBuilder of(String msg) {
      this.message = msg;
      return this;
    }



    public MessageBuilder color_foreground(Color color) {
      this.color = color;
      return this;
    }

    public MessageBuilder reset() {
      this.color = Color.RESET;
      return this;
    }
  }

  public enum Color {
    BLACK ("\\e[0;30m", "\\e[1;30m", "\\e[4;30m", "\\e[40m"),
    RED   ("\\e[0;31m", "\\e[1;31m", "\\e[4;31m", "\\e[41m"),
    GREEN ("\\e[0;32m", "\\e[1;32m", "\\e[4;32m", "\\e[42m"),
    YELLOW("\\e[0;33m", "\\e[1;33m", "\\e[4;33m", "\\e[43m"),
    BLUE  ("\\e[0;34m", "\\e[1;34m", "\\e[4;34m", "\\e[44m"),
    PURPLE("\\e[0;35m", "\\e[1;35m", "\\e[4;35m", "\\e[45m"),
    CYAN  ("\\e[0;36m", "\\e[1;36m", "\\e[4;36m", "\\e[46m"),
    WHITE ("\\e[0;37m", "\\e[1;37m", "\\e[4;37m", "\\e[47m"),
    RESET("\\e[0m"),
    ;

    public final String regular;
    public final String bold;
    public final String underline;
    public final String background;

    Color(String regular, String bold, String underline, String background) {
      this.regular    = regular;
      this.bold       = bold;
      this.underline  = underline;
      this.background = background;
    }

    Color(String code) {
      this.regular    = code;
      this.bold       = code;
      this.underline  = code;
      this.background = code;
    }
  }

}
