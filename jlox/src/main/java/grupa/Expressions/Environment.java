package grupa.Expressions;

import grupa.Parser.RuntimeError;
import grupa.Scanner.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();

    public void define(String variable, Object value) {
        values.put(variable, value);
    }

    public Object get(Token name) throws RuntimeError {
        if (values.containsKey(name.getLexeme())) {
            return values.get(name.getLexeme());
        }
        throw new RuntimeError(name, "Undefined variable'" + name.getLexeme() + "'." );
    }
}
