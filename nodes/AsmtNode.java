package nodes;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;


public class AsmtNode implements BodyStmtNode{
    private IdNode id;
    private ExprNode expr;
    private Token semiColon;
    private Token equal;

    /**
     *  Grammar: < asmt > -> <id >= < expr >
     * 
     * @param id child node that is a id
     * @param expr child node that is an expression
     * @param semiColon semicolon expected at end
     * @param equal assign expected between two nodes
     */

    public AsmtNode(IdNode id, ExprNode expr, Token equal, Token semiColon){
        this.id = id;
        this.expr = expr; 
        this.semiColon = semiColon;
        this.equal = equal;
    }

    /**
     *  parses a Asmt Node with the list of remaining tokens
     * 
     * @param tokens                    arraylist of tokens
     * @return                          Define AsmtNode
     * @throws SyntaxErrorException     One of child Nodes was incorrect
     */
    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws SyntaxErrorException{


        IdNode idNode = IdNode.parseId(tokens);
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        } 
        Token Equal = tokens.remove(0);
        if(Equal.getTokenType()!=TokenType.ASSIGN){
            throw new SyntaxErrorException("Expected Assign, found: " + Equal.getTokenType(), Equal);
        } 
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token semiColon = tokens.remove(0);
        if(semiColon.getTokenType()==TokenType.SEMICOLON){
            return new AsmtNode(idNode, exprNode, semiColon, Equal);        
        } else {
            throw new SyntaxErrorException("Expected SemiColon, found: " + semiColon.getTokenType(), semiColon );
        }

    }

    // returns both child nodes with an "=" inbetween them
    @Override
    public String convertToJott() {
        return id.convertToJott() + "=" + expr.convertToJott() + ";";
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
        String exprType = this.expr.getResultingType();
        String varType = JottParser.symTable.varSymTab.get(this.id.toString());
    
        // Ensure both the variable and expression types are found and not null
        if (varType != null && exprType != null) {
            // Check if the variable type matches the expression type
            if (varType.equals(exprType)) {
                // Types match, so the tree is semantically valid for this part
                return true;
            } else {
                // Types do not match, throw a semantic error exception
                throw new SemanticErrorException("Semantic Error: Mismatched Types. Expected " + varType + ", got " + exprType + " for " + id.getToken(), this.expr.getToken());
            }
        } else {
            // One or both of the types are null, indicating a missing type or undeclared variable
            throw new SemanticErrorException("Semantic Error: Missing type information for " + (varType == null ? "variable " + id.getToken() : "expression " + expr.getToken()), this.expr.getToken());
        }
    }

    @Override
    public String getReturnType() throws SemanticErrorException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReturnType'");
    }    
    
}
