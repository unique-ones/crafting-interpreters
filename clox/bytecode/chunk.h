#ifndef clox_chunk_h
#define clox_chunk_h

#include "../common.h"
#include "../instructions/values.h"

typedef enum {
    OP_CONSTANT,
    OP_CONSTANT_LONG,
    OP_NEGATE,
    OP_SUBTRACT,
    OP_MULTIPLY,
    OP_DIVIDE,
    OP_ADD,
    OP_RETURN,
} OpCode;

//line struct containing line and corresponding offset of the code
typedef struct {
    int offset;
    int line;
} CodeLine;


//@TODO change line count to smth. less memory hungry
typedef struct {
    int count;
    int capacity;
    uint8_t* code;
    int lineCount;
    int lineCapacity;
    CodeLine* code_line;
    ValueArray constants;
} Chunk;

void initChunk(Chunk* chunk);

void writeChunk(Chunk* chunk, uint8_t byte, int line);

void writeConstant(Chunk* chunk, Value value, int line);

int addConstant(Chunk* chunk, Value value);

void freeChunk(Chunk* chunk);

int getLine(Chunk* chunk, int offset);

#endif
