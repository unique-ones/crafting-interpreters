package grupa.Statements;

import grupa.Scanner.Token;

import java.util.List;

public class Function extends Stmt {

    private final List<Token> params;
    private final Token name;
    private final List<Stmt> body;

    public Function(List<Token> params, Token name, List<Stmt> body) {
        this.params = params;
        this.name = name;
        this.body = body;
    }

    public List<Token> getParams() {
        return params;
    }

    public Token getName() {
        return name;
    }

    public List<Stmt> getBody() {
        return body;
    }
    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitFunctionStatement(this);
    }
}
