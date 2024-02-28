package nodes;

import provided.Token;
import provided.TokenType;
import provided.JottParser;
import provided.JottTree;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;


public interface OperandNode extends ExprNode{

    /**
     *  checks if node is an operand node
     * 
     * @param tokens                    arraylist of tokens
     * @return                          OperandNode
     * @throws SyntaxErrorException     Node is not a valid OperandNode
     */

    public static OperandNode parseOperandNode(ArrayList<Token> tokens) throws SyntaxErrorException {

        // errors if EOF
        if (tokens == null || tokens.isEmpty()){
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token token = tokens.get(0);
        //  Checks if its an ID
        if(token.getTokenType() == TokenType.ID_KEYWORD){
            return IdNode.parseId(tokens);
        // Checks if its an Number
        } else if(token.getTokenType() == TokenType.NUMBER){
            return NumNode.parseNum(tokens);
        // Checks if its a function call
        } else if(token.getTokenType() == TokenType.FC_HEADER){
            return FunctionCallNode.parseFunctionCallNode(tokens);
        // Checks if its a Negative Number
        } else if(token.getTokenType() == TokenType.MATH_OP){
            return NumNode.parseNum(tokens);
        // Invalid
        } else{
            throw new SyntaxErrorException("Expected Operand", token);
        }

    }

    @Override
    public String convertToJott();

    @Override
    public String convertToJava(String className);

    @Override
    public String convertToC();

    @Override
    public String convertToPython();

    @Override
    public boolean validateTree();
    
}
