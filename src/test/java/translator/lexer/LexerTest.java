package translator.lexer;

import translator.lexer.token.Token;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;

import static translator.lexer.token.Token.Type.BIN_OP;
import static translator.lexer.token.Token.Type.NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class LexerTest {
    @Test
    public void shouldReturnEmptyTokensCollectionWhenInputIsEmpty() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).isEmpty();
    }

    @Test
    public void shouldMatchPlusSign() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("+");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).contains(new Token("+", BIN_OP, 1));
    }

    @Test
    public void shouldMatchMinusSign() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("-");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).contains(new Token("-", BIN_OP, 1));
    }

    @Test
    public void shouldMatchIntegers() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("42");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).contains(new Token("42", NUMBER, 1));
    }

    @Test
    public void shouldMatchSimpleArithmeticExpressions() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("4 + 6 - 2");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).contains(
                new Token("4", NUMBER, 1),
                new Token("+", BIN_OP, 1),
                new Token("6", NUMBER, 1),
                new Token("-", BIN_OP, 1),
                new Token("2", NUMBER, 1)
        );
    }

    @Test
    public void shouldCountLines() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("\n42");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).contains(new Token("42", NUMBER, 2));
    }

    @Test
    public void shouldIgnoreWhitespacesAndTabulations() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("+ \t - ");
        // When
        Collection<Token> tokens = Lexer.scan(inputStream);
        // Then
        assertThat(tokens).contains(
                new Token("+", BIN_OP, 1),
                new Token("-", BIN_OP, 1));
    }

    @Test
    public void shouldThrowExceptionForUnknownSymbols() {
        // Given
        InputStream inputStream = IOUtils.toInputStream("*");
        // When
        Throwable thrown = catchThrowable(() -> {
           Lexer.scan(inputStream);
        });
        // Then
        assertThat(thrown).hasMessage("Unknown symbol *");
    }
}
