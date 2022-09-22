package io.ansan.nas.aarch64.op;

import io.ansan.nas.asm.Register;

public enum AArch64Reg {
  R0 (0x01, "r0" , "Scratch register #0" ),
  R1 (0x01, "r1" , "Scratch register #1" ),
  R2 (0x0, "r2" , "Scratch register #2" ),
  R3 (0x0, "r3" , "Scratch register #3" ),
  R4 (0x0, "r4" , "Scratch register #4" ),
  R5 (0x0, "r5" , "Scratch register #5" ),
  R6 (0x0, "r6" , "Scratch register #6" ),
  R7 (0x0, "r7" , "Scratch register #7" ),
  R8 (0x0, "r8" , "Scratch register #8" ),
  R9 (0x0, "r9" , "Scratch register #9" ),
  R10(0x0, "r10", "Scratch register #10"),
  R11(0x0, "r11", "Scratch register #11"),
  R12(0x0, "r12", "Scratch register #12"),
  R13(0x0, "r13", "Scratch register #13"),
  R14(0x0, "r14", "Scratch register #14"),
  R15(0x0, "r15", "Scratch register #15"),
  R16(0x0, "r16", "Scratch register #16"),
  R17(0x0, "r17", "Scratch register #17"),
  R18(0x0, "r18", "Scratch register #18"),
  R19(0x0, "r19", "Scratch register #19"),
  R20(0x0, "r20", "Scratch register #20"),
  R21(0x0, "r21", "Scratch register #21"),
  R22(0x0, "r22", "Scratch register #22"),
  R23(0x0, "r23", "Scratch register #23"),
  R24(0x0, "r24", "Scratch register #24"),
  R25(0x0, "r25", "Scratch register #25"),
  R26(0x0, "r26", "Scratch register #26"),
  R27(0x0, "r27", "Scratch register #27"),
  R28(0x0, "r28", "Scratch register #28"),
  R29(0x0, "r29", "Scratch register #29"),
  R30(0x0, "r30", "Scratch register #30"),
  ;

  public final int id;
  public final String name;
  public final String description;

  AArch64Reg(int id, String name, String description) {
      this.id         = id;
      this.name       = name;
      this.description = description;
  }

  public Register<AArch64Reg> to_register() {
      return new Register<>(name, id, description, this);
  }
}
