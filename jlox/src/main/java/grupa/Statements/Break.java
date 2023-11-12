package grupa.Statements;

public class Break extends Stmt {
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitBreakStatement(this);
    }
}
