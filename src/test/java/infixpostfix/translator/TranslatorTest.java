package infixpostfix.translator;

import infixpostfix.parser.Parser.BinOpNode;
import infixpostfix.parser.Parser.ExprNode;
import infixpostfix.parser.Parser.NumberNode;
import infixpostfix.parser.Parser.RestNode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TranslatorTest {
    @Test
    public void shouldTranslateToPostfixNotation() {
        // Given
        ExprNode exprNode = new ExprNode(
                new NumberNode("8"),
                new RestNode(
                        new BinOpNode("+"),
                        new NumberNode("4"),
                        new RestNode(
                                new BinOpNode("-"),
                                new NumberNode("2"),
                                null
                        )
                )
        );
        // When
        String translation = Translator.translate(exprNode);
        // Then
        assertThat(translation).isEqualTo("8 4 + 2 -");
    }
}
