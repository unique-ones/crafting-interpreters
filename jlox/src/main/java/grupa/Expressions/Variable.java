package grupa.Expressions;

import grupa.Interpreter.RuntimeError;
import grupa.Scanner.Token;

public class Variable extends Expr{
    private final Token name;

    public Variable(Token name) {
        this.name = name;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) throws RuntimeError {
        return exprVisitor.visitVariableExpression(this);
    }

    public Token getName() {
        return name;
    }
}
