package grupa.Statements;

import grupa.Expressions.Expr;
import grupa.Interpreter.RuntimeError;

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
    public <R> R accept(StmtVisitor<R> stmtVisitor) throws RuntimeError {
        return stmtVisitor.visitWhileStatement(this);
    }
}
