package io.ansan.nas.aarch64;

import io.ansan.nas.aarch64.op.AArch64Op;
import io.ansan.nas.asm.OpCode;

public final class AArchOpCodes {
    public static final OpCode NOP = AArch64Op.NOP.toInstruction();
}
