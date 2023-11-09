package grupa.Statements;

import grupa.Interpreter.RuntimeError;

import java.sql.Statement;

public interface StmtVisitor<R> {
    R visitExpressionStatement(Expression statement) throws RuntimeError;
    R visitPrintStatement(Print statement) throws RuntimeError;
    R visitVarStatement(Var statement) throws RuntimeError;
    R visitBlockStatement(Block block) throws RuntimeError;
    R visitIfStatement(If Statement) throws RuntimeError;
    R visitWhileStatement(While statement) throws RuntimeError;



}
