package nodes;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;


public class AsmtNode implements BodyStmtNode{
    private IdNode id;
    private ExprNode expr;

    /**
     *  Grammar: < asmt > -> <id >= < expr >
     * 
     * @param id child node that is a id
     * @param expr child node that is an expression
     * 
     */

    public AsmtNode(IdNode id, ExprNode expr){
        this.id = id;
        this.expr = expr; 
    }

    /**
     *  parses a Asmt Node with the list of remaining tokens
     * 
     * @param tokens                    arraylist of tokens
     * @return                          Define AsmtNode
     * @throws SyntaxErrorException     One of child Nodes was incorrect
     */
    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws SyntaxErrorException{

        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        IdNode idNode = IdNode.parseId(tokens);
        return new AsmtNode(idNode, exprNode);
    }

    // returns both child nodes with an "=" inbetween them
    @Override
    public String convertToJott() {
        return id.convertToJott() + "=" + expr.convertToJott();
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
