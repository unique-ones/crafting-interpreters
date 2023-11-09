package grupa.Expressions;

import grupa.Interpreter.RuntimeError;
import grupa.Scanner.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final Environment enclosing;

    public Environment() {
        enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    private final Map<String, Object> values = new HashMap<>();

    public void define(Token variable, Object value) {
        values.put(variable.getLexeme(), value);
    }

    public void assign(Token variable, Object value) throws RuntimeError {
        if (values.containsKey(variable.getLexeme())) {
            values.put(variable.getLexeme(), value);
            return;
        }
        if (enclosing != null) {
            enclosing.assign(variable, value);
            return;
        }
        throw new RuntimeError(variable, "Undefined variable'" + variable.getLexeme() + "'.");
    }

    public Object get(Token variable) throws RuntimeError {
        if (values.containsKey(variable.getLexeme())) {
            Object value = values.get(variable.getLexeme());
            if (value == null) {
                throw new RuntimeError(variable, " Variable not initialized before use'" + variable.getLexeme() + "'.");

            }
            return values.get(variable.getLexeme());
        }
        if (enclosing != null) {
            return enclosing.get(variable);
        }
        throw new RuntimeError(variable, "Undefined variable'" + variable.getLexeme() + "'.");
    }
}
