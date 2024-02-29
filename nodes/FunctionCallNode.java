package nodes;

import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;

public class FunctionCallNode implements BodyStmtNode, OperandNode {

    private IdNode idNode;
    private ParamsNode paramsNode;

    /**
     * Grammar: < func_call > --> :: < id >[< params >]
     * 
     * @param idnode     IdNode child node     
     * @param paramsNode ParamsNode child node
     */
    public FunctionCallNode(IdNode idNode, ParamsNode paramsNode) {
        this.idNode = idNode;
        this.paramsNode = paramsNode;
    }

    /**
     * Parses a function call node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      FunctionCallNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        // Look for ::
        Token fcHeadToken = tokens.remove(0);
        if(fcHeadToken.getTokenType() != TokenType.FC_HEADER){
            throw new SyntaxErrorException("Expected :: but got " + fcHeadToken.getToken(), fcHeadToken);
        }

        // Look for <id>
        IdNode idNode = IdNode.parseId(tokens);
        
        // Look for [
        Token lbToken = tokens.remove(0);
        if(lbToken.getTokenType() != TokenType.L_BRACKET){
            throw new SyntaxErrorException("Missing left square bracket " + lbToken.getToken(), fcHeadToken);
        }

        // Look for <params>
        ParamsNode paramsNode = ParamsNode.parseParamsNode(tokens);

        // Look for ]
        Token rbToken = tokens.remove(0);
        if(rbToken.getTokenType() != TokenType.R_BRACKET){
            throw new SyntaxErrorException("Missing right square bracket " + lbToken.getToken(), fcHeadToken);
        }

        return new FunctionCallNode(idNode, paramsNode);
    }

    
    @Override
    public String convertToJott() {
        return "::" + idNode.convertToJott() + "[" + paramsNode.convertToJott() + "]";
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
