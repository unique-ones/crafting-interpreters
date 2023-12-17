//this file only has maintenance purposes, only here to dissamlble at aa given offset
#include "debug.h"
#include <stdio.h>

//prototypes
int simpleInstruction(char*, int);

int constantInstruction(char*, Chunk*, int);

//function to dissemble one Chunk
void dissembleChunk(Chunk* chunk, const char* name) {
    //Also prints header of chunk in order to knwo which chunk we are looking at
    printf("===%s===\n", name);
    //let offset be incremented by dissembleInstruction as an instuction can have different sizes
    for (int offset = 0; offset < chunk->count;) {
        offset = dissembleInstruction(chunk, offset);
    }
}

//dissamble one instruction in a chunk at a sepcial offset
int dissembleInstruction(Chunk* chunk, int offset) {
    // print offset with 4 minimum field width
    printf("%04d ", offset);
    if (offset > 0 && getLine(chunk, offset) == getLine(chunk, offset - 1)) {
        printf("    | ");
    } else {
        printf("%4d ", getLine(chunk, offset));
    }
    uint8_t instruction = chunk->code[offset];
    switch (instruction) {
        case OP_CONSTANT:
            return constantInstruction("OP_CONSTANT", chunk, offset);
        case OP_RETURN:
            return simpleInstruction("OP_RETURN", offset);
        default:
            printf("Unknown code %d\n", instruction);
            return offset + 1;
    }
}

int constantInstruction(char* name, Chunk* chunk, int offset) {
    //Get one-byte constant index operand, which is directly stored after 'OP_CONSTANT'
    uint8_t constant_index = chunk->code[offset + 1];
    printf("%-16s %4d ", name, constant_index);
    printValue(chunk->constants.values[constant_index]);
    printf("\n");
    return offset + 2;
}

int simpleInstruction(char* name, int offset) {
    printf("%s\n", name);
    return offset + 1;
}
