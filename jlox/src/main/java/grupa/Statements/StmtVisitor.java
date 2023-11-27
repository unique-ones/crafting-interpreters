package grupa.Statements;

public interface StmtVisitor<R> {
    R visitExpressionStatement(Expression statement);

    R visitPrintStatement(Print statement);

    R visitVarStatement(Var statement);

    R visitBlockStatement(Block block);

    R visitIfStatement(If Statement);

    R visitWhileStatement(While statement);

    R visitBreakStatement(Break statement);

    R visitContinueStatement(Continue statement);

    R visitFunctionStatement(Function statement);

    R visitReturnStatement(Return statement);

    R visitClassStatement(Class statement);

}
