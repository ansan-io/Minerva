package io.ansan.nas.asm;

public record OpCode<T>(String name, int instruction, String description, T raw_instruction) {
}
