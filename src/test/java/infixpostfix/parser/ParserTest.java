package infixpostfix.parser;

import infixpostfix.lexer.token.Token;
import infixpostfix.parser.Parser.ExprNode;
import infixpostfix.parser.Parser.RestNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ParserTest {
    @Test
    public void shouldParseNumbers() {
        // Given
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("8", Token.Type.NUMBER, 1));
        //When
        ExprNode node = Parser.parse(tokens);
        //Then
        assertThat(node.getNumber().getLexeme())
                .isEqualTo("8");
        assertThat(node.getRest())
                .isNull();
    }

    @Test
    public void shouldParseWellFormedExpressions() {
        // Given
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("8", Token.Type.NUMBER, 1));
        tokens.add(new Token("+", Token.Type.BIN_OP, 1));
        tokens.add(new Token("4", Token.Type.NUMBER, 1));
        //When
        ExprNode exprNode = Parser.parse(tokens);
        //Then
        assertThat(exprNode.getNumber().getLexeme())
                .isEqualTo("8");
        assertThat(exprNode.getRest())
                .isNotNull();

        RestNode restNode = exprNode.getRest();

        assertThat(restNode.getBinOp().getLexeme())
                .isEqualTo("+");

        assertThat(restNode.getNumber().getLexeme())
                .isEqualTo("4");

        assertThat(restNode.getRest())
                .isNull();
    }

    @Test
    public void shouldThrowExceptionForMalformedInput() {
        // Given
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("8", Token.Type.NUMBER, 1));
        tokens.add(new Token("4", Token.Type.NUMBER, 1));
        //When
        Throwable thrown = catchThrowable(() -> {
            Parser.parse(tokens);
        });
        //Then
        assertThat(thrown).hasMessage("Error at line 1. Unexpected symbol 4.");
    }
}
