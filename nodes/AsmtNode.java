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
    private String funcId;
    /**
     *  Grammar: < asmt > -> <id >= < expr >
     * 
     * @param id child node that is a id
     * @param expr child node that is an expression
     * @param semiColon semicolon expected at end
     * @param equal assign expected between two nodes
     */

    public AsmtNode(IdNode id, ExprNode expr, String funcId){
        this.id = id;
        this.expr = expr; 
        this.funcId = funcId;
    }

    /**
     *  parses a Asmt Node with the list of remaining tokens
     * 
     * @param tokens                    arraylist of tokens
     * @return                          Define AsmtNode
     * @throws SyntaxErrorException     One of child Nodes was incorrect
     */
    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException{


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
            return new AsmtNode(idNode, exprNode, funcId);        
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
        return id.convertToJava(className) + " = " + expr.convertToJava(className) + ";\n";
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return id.convertToC() + " = " + expr.convertToC() + ";\n";
    }

    @Override
    public String convertToPython(int tabs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        expr.validateTree();

        String exprType = this.expr.getReturnType();
        String[] varData = JottParser.symTable.varSymTab.get(this.id.toString());

        if(!varData[1].equals(funcId)) {
            throw new SemanticErrorException("Unkown variable " + varData[0], expr.getToken());
        }

        String varType = varData[0];
    
        // Ensure both the variable and expression types are found and not null
        if (varType != null && exprType != null) {
            // Check if the variable type matches the expression type
            if (varType.equals(exprType)) {
                // Types match, so the tree is semantically valid for this part 
                return true;
            } else {
                // Types do not match, throw a semantic error exception
                throw new SemanticErrorException("Mismatched Types. Expected " + varType + ", got " + exprType + " for " + id.toString(), this.expr.getToken());
            }
        } else {
            // One or both of the types are null, indicating a missing type or undeclared variable
            throw new SemanticErrorException("Missing type information for " + (varType == null ? "variable " + id.toString() : "expression " + expr.toString()), this.expr.getToken());
        }
    }

    @Override
    public boolean doesAllReturn() {
        return false;
    }
    
}
