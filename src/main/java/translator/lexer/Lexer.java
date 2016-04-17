package translator.lexer;

import translator.lexer.token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Character.isDigit;
import static translator.lexer.token.Token.Type.BIN_OP;
import static translator.lexer.token.Token.Type.NUMBER;

public class Lexer {
    public static Collection<Token> scan(InputStream input) {
        long line = 1;
        List<Token> tokens = new ArrayList<>();

        try {
            while(input.available() > 0) {
                char peek = nextCharFrom(input);

                if(peek == ' ' || peek == '\t') {
                    // Do nothing
                } else if(peek == '+') {
                    tokens.add(new Token("+", BIN_OP, line));

                } else if(peek == '-') {
                    tokens.add(new Token("-", BIN_OP, line));

                } else if(isDigit(peek)) {
                    StringBuffer integer = new StringBuffer();

                    while (isDigit(peek)) {
                        integer.append(peek); peek = nextCharFrom(input);
                    }

                    tokens.add(new Token(integer.toString(), NUMBER, line));

                } else if(peek == '\n') {
                    line++;
                } else {
                    throw new RuntimeException("Unknown symbol " + peek);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return tokens;
    }

    private static char nextCharFrom(InputStream input) throws IOException {
        return (char) input.read();
    }
}
