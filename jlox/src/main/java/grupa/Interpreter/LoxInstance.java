package grupa.Interpreter;

import grupa.Interpreter.Exceptions.RuntimeError;
import grupa.Scanner.Token;
import grupa.Statements.LoxClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    public Object get(Token name) {
        if (fields.containsKey(name.getLexeme())) {
            return fields.get(name.getLexeme());
        }
        throw new RuntimeError(name, "Undefined property '" + name.getLexeme() + "'.");

    }

    @Override
    public String toString() {
        return "LoxInstance{" +
                "klass=" + klass +
                '}';
    }
}
