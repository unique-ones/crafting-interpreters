package grupa.Statements;

public class Continue extends Stmt{
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitContinueStatement(this);
    }
}
