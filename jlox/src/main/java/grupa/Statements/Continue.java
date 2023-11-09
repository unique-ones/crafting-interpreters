package grupa.Statements;

import grupa.Interpreter.RuntimeError;

public class Continue extends Stmt{
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitContinueStatement(this);
    }
}
