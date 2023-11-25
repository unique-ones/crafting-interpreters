package grupa.Expressions;

import grupa.Interpreter.Exceptions.RuntimeError;
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

    public void define(String name, Object value) {
        values.put(name, value);
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

    public Object get(Token variable) {
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

    public Object getAt(Integer distance, String name) {
        return ancestor(distance).values.get(name);
    }

    private Environment ancestor(Integer distance) {
        Environment environment = this;
        for (int i = 0; i < distance; i++) {
            environment = environment.enclosing;
        }
        return environment;
    }

    public void assignAt(Integer distance, Token name, Object value) {
        ancestor(distance).values.put(name.getLexeme(), value);
    }
}
