//this file only has maintenance purposes, only here to dissamlble at aa given offset
#include "debug.h"
#include <stdio.h>

//prototypes
int simpleInstruction(char*, int);


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


    uint8_t instruction = chunk->code[offset];
    switch (instruction) {
        case OP_RETURN:
            return simpleInstruction("OP_RETURN", offset);
        default:
            printf("Unknown code %d\n", instruction);
            return offset + 1;
    }
}


int simpleInstruction(char* str, int offset) {
    printf("%s\n", str);
    return offset + 1;
}
