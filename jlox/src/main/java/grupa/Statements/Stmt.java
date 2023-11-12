package grupa.Statements;

public abstract class Stmt {
    public abstract <R> R accept(StmtVisitor<R> stmtVisitor) ;
}
