package grupa.Statements;

import grupa.Expressions.Expr;
import grupa.Parser.RuntimeError;
import grupa.Scanner.Token;

public class Var extends Stmt {
    private final Token name;
    private final Expr initializer;

    public Var(Token name, Expr initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public Expr getInitializer() {
        return initializer;
    }

    public Token getName() {
        return name;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) throws RuntimeError {
        return stmtVisitor.visitVarStatement(this);
    }
}
