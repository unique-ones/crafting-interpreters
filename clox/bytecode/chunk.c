#include "chunk.h"

void initChunk(Chunk* chunk) {
    chunk->capacity = 0;
    chunk->count = 0;
    chunk->code = NULL;
    chunk->lineCapacity = 0;
    chunk->lineCount = 0;
    chunk->code_line = NULL;
    initValueArray(&chunk->constants);
}

void writeChunk(Chunk* chunk, uint8_t byte, int line) {
    if (chunk->capacity < chunk->count + 1) {
        int oldcapacity = chunk->capacity;
        chunk->capacity = GROW_CAPACITY(oldcapacity);
        chunk->code = GROW_ARRAY(uint8_t, chunk->code, oldcapacity, chunk->capacity);
    }
    chunk->code[chunk->count] = byte;
    chunk->count++;

    if (chunk->lineCount > 0 && chunk->code_line[chunk->lineCount - 1].line == line) {
        return;
    }
    if (chunk->lineCapacity < chunk->lineCount + 1) {
        int oldcapacity = chunk->lineCapacity;
        chunk->lineCapacity = GROW_CAPACITY(oldcapacity);
        chunk->code_line = GROW_ARRAY(CodeLine, chunk->code_line, oldcapacity, chunk->lineCapacity);
    }
    CodeLine* code_line = &chunk->code_line[chunk->lineCount++];
    code_line->line = line;
    code_line->offset = chunk->count - 1;
}

//writing constant has own method, to also storing numbers up to 24bit
void writeConstant(Chunk* chunk, Value value, int line) {
    int index_constant = addConstant(chunk, value);
    //8bit implementation
    if (index_constant < 256) {
        writeChunk(chunk, OP_CONSTANT, line);
        writeChunk(chunk, index_constant, line);
        return;
    }
    //24bit implementation 3*8bit calls
    writeChunk(chunk, OP_CONSTANT_LONG, line);
    writeChunk(chunk, (uint8_t)(index_constant & 0xff), line);
    writeChunk(chunk, (uint8_t)((index_constant >> 8) & 0xff), line);
    writeChunk(chunk, (uint8_t)((index_constant >> 16) & 0xff), line);

}

void freeChunk(Chunk* chunk) {
    FREE_ARRAY(uint8_t, chunk->code, chunk->capacity);
    FREE_ARRAY(CodeLine, chunk->code_line, chunk->lineCapacity);
    freeValueArray(&chunk->constants);
    initChunk(chunk);
}


int addConstant(Chunk* chunk, Value value) {
    writeValueArray(&chunk->constants, value);
    return chunk->constants.count - 1;
}

//using binary search to find corresponding line struct
//we can assume that array is sorted, as the line car only increment during compiling
int getLine(Chunk* chunk, int offset) {
    int start = 0;
    int end = chunk->lineCount - 1;

    for (;;) {
        int mid = start + ((end - start) / 2);
        CodeLine* code_line = &chunk->code_line[mid];
        //if offset smaller
        if (offset < code_line->offset) {
            end = mid - 1;
        }
        /*as we only safe the FIRST offset that appears in a line we have to check if
        offset is smaller than next Code_Line offset, so we know its the same line */
        //if line is at last element, then mid==end
        else if (mid == chunk->lineCount - 1 || offset < chunk->code_line[mid + 1].offset) {
            return code_line->line;
        }
        //if bigger
        else {
            start = mid + 1;
        }
    }
}
