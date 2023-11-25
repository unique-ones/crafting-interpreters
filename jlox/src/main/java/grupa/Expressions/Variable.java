package grupa.Expressions;

import grupa.Resolver.VariableState;
import grupa.Scanner.Token;

public class Variable extends Expr{
    private final Token name;
    public VariableState variableState;

    public Variable(Token name, VariableState variableState) {
        this.name = name;
        this.variableState = variableState;
    }

    public Variable(Token name) {
        this.name = name;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor)  {
        return exprVisitor.visitVariableExpression(this);
    }

    public Token getName() {
        return name;
    }
}
