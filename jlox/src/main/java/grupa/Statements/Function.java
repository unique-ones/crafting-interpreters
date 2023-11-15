package grupa.Statements;

import grupa.Scanner.Token;

import java.util.List;

public class Function extends Stmt {

    private final Token name;
    private final grupa.Expressions.Function declaration;

    public Function(Token name, grupa.Expressions.Function declaration) {
        this.name = name;
        this.declaration = declaration;
    }

    public List<Token> getParams() {
        return this.declaration.getParamters();
    }

    public Token getName() {
        return name;
    }

    public List<Stmt> getBody() {
        return this.declaration.getBody();
    }

    public grupa.Expressions.Function getDeclaration() {
        return declaration;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitFunctionStatement(this);
    }
}
