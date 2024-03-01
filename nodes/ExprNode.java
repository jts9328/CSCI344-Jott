package nodes;

import java.util.ArrayList;

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
        else if (token1.getToken().equals("True") || token1.getToken().equals("False")){
            return BoolNode.parseBoolNode(tokens);
        }
        // if EOF check if its only an Operand
        else if(tokens.size()<3){
           return OperandNode.parseOperandNode(tokens);
        } 
        else{
            Token token2 = tokens.get(1);
            // check if next token is a valid operation
            if((token2.getTokenType()==TokenType.REL_OP) || token2.getTokenType()==TokenType.MATH_OP){
                OperandNode firstOperand = OperandNode.parseOperandNode(tokens);
                OpNode op = OpNode.parseOp(tokens);
                OperandNode secondOperand = OperandNode.parseOperandNode(tokens);
                // Helper object to allow one return
                return new ExprNodeHelper(firstOperand,op,secondOperand);
            } else { 
                // returns only Operand if next node isn't an operation
                return OperandNode.parseOperandNode(tokens);
                }
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
