package infixpostfix.parser;

import infixpostfix.lexer.token.Token;
import lombok.Value;

import java.util.Collection;
import java.util.Iterator;

public class Parser {
    private static ParserState parserState;

    public static ExprNode parse(Collection<Token> tokens) {
        parserState = new ParserState(tokens);
        return expr();
    }

    private static ExprNode expr() {
        return new ExprNode(number(), rest());
    }

    private static RestNode rest() {
        Token token = parserState.currentToken();

        switch(token.getType()) {
            case BIN_OP:
                return new RestNode(binOpNode(), number(), rest());
            case EMPTY_WORD:
                return null;
            default:
                throw new RuntimeException(
                        "Error at line " + token.getLine() + ". Unexpected symbol " + token.getLexeme() + ".");
        }
    }

    private static NumberNode number() {
        Token token = parserState.currentToken();

        if(token.getType().equals(Token.Type.NUMBER)) {
            parserState.consumeInput();
            return new NumberNode(token.getLexeme());
        } else {
            throw new RuntimeException(
                    "Error at line " + token.getLine() + ". Expecting number but was "+ token.getLexeme() + ".");
        }
    }

    private static BinOpNode binOpNode() {
        Token token = parserState.currentToken();

        if(token.getType().equals(Token.Type.BIN_OP)) {
            parserState.consumeInput();
            return new BinOpNode(token.getLexeme());
        } else {
            throw new RuntimeException(
                    "Error at line " + token.getLine() + ". Expecting binary operator but was "+ token.getLexeme() + ".");
        }
    }

    /*
        Parser state container
     */
    private static class ParserState {
        private Token currentToken;

        private Iterator<Token> it;

        public ParserState(Collection<Token> tokens) {
            it = tokens.iterator(); currentToken = it.next();
        }

        public Token currentToken() {
            return currentToken;
        }

        public void consumeInput() {
            if(it.hasNext()) {
                currentToken = it.next();
            } else {
                currentToken = new Token("", Token.Type.EMPTY_WORD, currentToken.getLine());
            }
        }
    }

    /*
        Parse tree node types
     */
    @Value
    public static class ExprNode {
        private NumberNode number;
        private RestNode rest;
    }

    @Value
    public static class RestNode {
        private BinOpNode binOp;
        private NumberNode numberNode;
        private RestNode rest;
    }

    @Value
    public static class BinOpNode {
        private String lexeme;
    }

    @Value
    public static class NumberNode {
        private String lexeme;
    }
}
