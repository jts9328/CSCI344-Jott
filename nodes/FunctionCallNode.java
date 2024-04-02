package nodes;

import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
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
            throw new SyntaxErrorException("Missing left square bracket, got " + lbToken.getToken(), lbToken);
        }

        // Look for <params>
        ParamsNode paramsNode = ParamsNode.parseParamsNode(tokens, idNode.toString());

        // Look for ]
        Token rbToken = tokens.remove(0);
        if(rbToken.getTokenType() != TokenType.R_BRACKET){
            throw new SyntaxErrorException("Missing right square bracket, got " + lbToken.getToken(), rbToken);
        }

        return new FunctionCallNode(idNode, paramsNode);
    }

    public String getResultingType() {
        // Retrieve the list associated with this function.
        ArrayList<String> typeInfo = JottParser.symTable.funcSymTab.get(this.idNode.toString());
    
        if (typeInfo != null && !typeInfo.isEmpty()) {
            // Return the last item from the list, which represents the function's return type.
            return typeInfo.get(typeInfo.size() - 1);
        } else {
            // Handle error
            return null;
        }
    }
    
    public Token getToken() {
        return null;
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
    public boolean validateTree() throws SemanticErrorException {
        // The function id provided does not exist
        if(JottParser.symTable.funcSymTab.containsKey(idNode.getToken().getToken())) {
            throw new SemanticErrorException("Semantic Error:\nCall to unknown function " + idNode.getToken().getToken(), idNode.getToken());
        }
        
        return paramsNode.validateTree();
    }

}
