package io.ansan.nas.parser;

public enum Kind {
    EOL                 ("EOL"),
    EOF                 ("EOF"),

    PARAN_OPEN          ("("),  // (
    PARAN_CLOSE         (")"),    // )
    BRACKET_OPEN        ("["),
    BRACKET_CLOSE       ("]"),
    COLON               (":"),        // :
    COMMA               (","),        // ,
    COMMENT             ("//"),      // //
    DIRECTIVE           ("@"),      // @
    TAB                 ("\t"),          // \t
    ASSIGN              ("="),       // =
    STRING              ("\""),       // "

    SCRATCH_REG_START   (null),
        S0              ("s0"),
        S1              ("s1"),
        S2              ("s2"),
        S3              ("s3"),
        S4              ("s4"),
        S5              ("s5"),
        S6              ("s6"),
        S7              ("s7"),
        S8              ("s8"),
        S9              ("s9"),
        S10             ("s10"),
        S11             ("s11"),
        S12             ("s12"),
        S13             ("s13"),
        S14             ("s14"),
        S15             ("s15"),
    SCRATCH_REG_END     (null),

    INSTRUCTION_START   (null),
        CALL            ("call"),
        STORE           ("store"),
        LOAD            ("load"),
        BRANCH          ("br"),
        MOV             ("mov"),
        ADD             ("add"),
        SUB             ("sub"),
        MUL             ("mul"),
        DIV             ("div"),
    INSTRUCTION_END     (null),

    IDENT               ("ident"),
    NUMBER              ("number"),
    ;
    public final String name;

    Kind(String name) {
        this.name = name;
    }
}
