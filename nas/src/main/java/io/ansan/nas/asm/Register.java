package io.ansan.nas.asm;

public record Register<T>(String op_name, int opcode, String description, T raw_register) {
}
