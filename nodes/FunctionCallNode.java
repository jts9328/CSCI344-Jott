package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionCallNode implements JottTree{
    private IdNode idnode;
    private ParamsNode paramsnode;

    public FunctionCallNode(IdNode idnode, ParamsNode paramsnode){
        this.paramsnode = paramsnode;
    }

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            // TODO Throw exception instead of NULL
            throw new SyntaxError()
        }

        Token fcHeadToken = tokens.remove(0);
        if(fcHeadToken.getTokenType() != TokenType.FC_HEADER){
            throw new SyntaxError()
        }

        IdNode idnode = IdNode.parseId(tokens);
        
        Token lbToken = tokens.remove(0);
        if(lbToken.getTokenType() != TokenType.L_BRACKET){
            throw new SyntaxError()
        }

        ParamsNode paramsNode = ParamsNode.parseParamsNode(tokens);

        Token rbToken = tokens.remove(0);
        if(rbToken.getTokenType() != TokenType.R_BRACKET){
            throw new SyntaxError()
        }
    }

    
    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
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
