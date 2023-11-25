package grupa.Statements;

import grupa.Scanner.Token;

public class Continue extends Stmt {
    private final Token continueToken;


    public Continue(Token continueToken) {
        this.continueToken = continueToken;
    }

    public Token getContinueToken() {
        return continueToken;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitContinueStatement(this);
    }
}
