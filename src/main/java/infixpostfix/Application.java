package infixpostfix;

import infixpostfix.lexer.Lexer;
import infixpostfix.lexer.token.Token;
import infixpostfix.parser.Parser;
import infixpostfix.parser.Parser.ExprNode;
import infixpostfix.translator.Translator;

import java.io.InputStream;
import java.util.Collection;

public class Application {
    public static void main(String[] args) {
        InputStream input = Application.class.getClassLoader().getResourceAsStream("example.exp");

        Collection<Token> tokens = Lexer.scan(input);

        ExprNode exprNode = Parser.parse(tokens);

        String translation = Translator.translate(exprNode);

        System.out.println(translation);
    }
}
