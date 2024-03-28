package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;


//
// < operand > -> <id > | <num > | < func_call > | -< num >
// < expr > -> < operand > | < operand > < relop > < operand > | < operand > < mathop > < operand > | < string_literal > | < bool >
//

public interface ExprNode extends JottTree {


    /**
     *  Checks if the following tokens is an expression
     * 
     * @param tokens                    Arraylist of tokens
     * @return                          Expression Node
     * @throws SyntaxErrorException     Not valid Expression Node
     */
    public static ExprNode parseExprNode(ArrayList<Token> tokens) throws SyntaxErrorException{
        if(tokens == null || tokens.isEmpty()){
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        // check only Next token
        Token token1 = tokens.get(0);
        
        // if Expressio Nnode is a String
        if(token1.getTokenType()==TokenType.STRING){
           return StringNode.parseString(tokens);
        }
        // Check if its a boolean
        if (token1.getToken().equals("True") || token1.getToken().equals("False")){
            return BoolNode.parseBoolNode(tokens);
        }

        OperandNode operandNode = OperandNode.parseOperandNode(tokens);

        if(tokens.get(0).getTokenType() == TokenType.REL_OP || tokens.get(0).getTokenType() == TokenType.MATH_OP) {
            OpNode opNode = OpNode.parseOp(tokens);
            OperandNode operandNode2 = OperandNode.parseOperandNode(tokens);
            return new ExprNodeHelper(operandNode, opNode, operandNode2);
        }

        // Next token is not an operator, therefore this is a single operand
        return operandNode;
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
    public boolean validateTree() throws SemanticErrorException;
    
}
