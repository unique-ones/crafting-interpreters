package grupa.Statements;

import grupa.Interpreter.RuntimeError;

import java.sql.Statement;

public interface StmtVisitor<R> {
    R visitExpressionStatement(Expression statement);

    R visitPrintStatement(Print statement);

    R visitVarStatement(Var statement);

    R visitBlockStatement(Block block);

    R visitIfStatement(If Statement);

    R visitWhileStatement(While statement);

    R visitBreakStatement(Break statement);

    R visitContinueStatement(Continue statement);


}
