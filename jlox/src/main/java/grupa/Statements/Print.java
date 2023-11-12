package grupa.Statements;

import grupa.Expressions.Expr;

public class Print extends Stmt {
    private final Expr expr;

    public Print(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpression() {
        return expr;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmVisitor)  {
        return stmVisitor.visitPrintStatement(this);
    }

}
