package infixpostfix.lexer.token;

import lombok.Value;

@Value
public class Token {
    private String lexeme;
    private Type type;
    private long line;

    public enum Type{
        NUMBER,
        BIN_OP
    }
}
