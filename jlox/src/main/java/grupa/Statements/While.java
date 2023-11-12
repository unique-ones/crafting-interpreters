package grupa.Statements;

import grupa.Expressions.Expr;

public class While extends Stmt {
    private final Expr condition;
    private final Stmt body;

    public While(Expr condition, Stmt body) {
        this.condition = condition;
        this.body = body;
    }

    public Expr getCondition() {
        return condition;
    }

    public Stmt getBody() {
        return body;
    }
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitWhileStatement(this);
    }
}
