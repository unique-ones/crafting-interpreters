package grupa.Interpreter;

import grupa.Statements.LoxClass;

public class LoxInstance {
    private LoxClass klass;

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    @Override
    public String toString() {
        return "LoxInstance{" +
                "klass=" + klass +
                '}';
    }
}
