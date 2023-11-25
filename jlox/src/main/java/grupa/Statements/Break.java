package grupa.Statements;

import grupa.Scanner.Token;

public class Break extends Stmt {
    private final Token breakToken;

    public Break(Token breakToken) {
        this.breakToken = breakToken;
    }

    public Token getBreakToken() {
        return breakToken;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor)  {
        return stmtVisitor.visitBreakStatement(this);
    }
}
