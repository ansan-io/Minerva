package io.ansan.nas.aarch64.op;

import io.ansan.nas.asm.OpCode;

public enum AArch64Op {
    NOP("NOP",0b0101, "No Operation"),
    ;

    public final int    op;
    public final String name;
    public final String description;

    AArch64Op(String name, int op, String description) {
        this.op          = op;
        this.name        = name;
        this.description = description;
    }

    public OpCode<AArch64Op> toInstruction() {
        return new OpCode<>(name, op, description, this);
    }
}
