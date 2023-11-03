package grupa.Statements;

import grupa.Expressions.Expr;
import grupa.Interpreter.RuntimeError;

public class If extends Stmt {
    final private Expr condition;
    final private Stmt thenBranch;
    final private Stmt elseBranch;

    public If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public Expr getCondition() {
        return condition;
    }

    public Stmt getThenBranch() {
        return thenBranch;
    }

    public Stmt getElseBranch() {
        return elseBranch;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) throws RuntimeError {
        return stmtVisitor.visitIfStatement(this);
    }
}
