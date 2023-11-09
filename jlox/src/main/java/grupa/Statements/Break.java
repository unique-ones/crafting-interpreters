package grupa.Statements;

import grupa.Interpreter.RuntimeError;

public class Break extends Stmt {
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitBreakStatement(this);
    }
}
