package infixpostfix.translator;

import com.sun.javafx.binding.StringFormatter;
import infixpostfix.parser.Parser.ExprNode;
import infixpostfix.parser.Parser.RestNode;

public class Translator {
    public static String translate(ExprNode exprNode) {
        return recursivelyTranslate(exprNode.getNumber().getLexeme(), exprNode.getRest());
    }

    private static String recursivelyTranslate(String leftOperand, RestNode rest) {
        if(rest == null) {
            return leftOperand;

        } else if(rest.getRest() == null) {
            String binOperator = rest.getBinOp().getLexeme();
            String rightOperand = rest.getNumber().getLexeme();
            return StringFormatter.format("%s %s %s", leftOperand, rightOperand, binOperator).getValue();

        } else {
            String binOperator = rest.getBinOp().getLexeme();
            String rightOperand = rest.getNumber().getLexeme();

            String partial = StringFormatter.format(
                    "%s %s %s", leftOperand, rightOperand, binOperator).getValue();

            RestNode recursiveRest = rest.getRest();

            return recursivelyTranslate(partial, recursiveRest);
        }
    }
}
