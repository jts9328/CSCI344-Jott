package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ParamsTNode implements JottTree {

    private ExprNode exprNode;

    /**
     * Grammar: < params_t > -->; ,< expr >;
     * 
     * @param exprNode exprNode
     */
    public ParamsTNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      ParamsTNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static ParamsTNode parseParamsTNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        // Look for ,
        Token commaToken = tokens.remove(0);
        if(commaToken.getTokenType() != TokenType.COMMA) {
            throw new SyntaxErrorException("Expected , but got " + commaToken.getToken(), commaToken);
        }

        // Look for <expr>
        ExprNode exprNode = ExprNode.parseExprNode(tokens);

        return new ParamsTNode(exprNode);
    }

    @Override
    public String convertToJott() {
        return "," + exprNode.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return "," + exprNode.convertToJava(className);
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        this.exprNode.validateTree();
        return true;
    }

    public String getReturnType() {
        return exprNode.getReturnType();
    }
    
}
