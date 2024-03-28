package nodes;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ParamsNode implements JottTree{

    private ExprNode exprNode;
    private ArrayList<ParamsTNode> paramsTNodes;

    /**
     * Grammar: < params > --> < expr > < params_t >*
     * 
     * @param exprNode      child node ExprNode 
     * @param paramsTNodes  arraylist of child nodes paramsTNode
     */
    public ParamsNode(ExprNode exprNode, ArrayList<ParamsTNode> paramsTNodes) {
        this.exprNode = exprNode;
        this.paramsTNodes = paramsTNodes;
    }

    /**
     * Grammar: < params > --> ε
     */
    public ParamsNode() {
        this(null, null);
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      ParamsNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static ParamsNode parseParamsNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        // If the next token is just a ], that means there are no params (ε)
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return new ParamsNode();
        }

        // Look for <expr>
        ExprNode exprNode = ExprNode.parseExprNode(tokens);

        // Look for <params_t>*
        ArrayList<ParamsTNode> paramsTNodes = new ArrayList<>();

        while(tokens.get(0).getTokenType() == TokenType.COMMA) {
            paramsTNodes.add(ParamsTNode.parseParamsTNode(tokens));
        }

        return new ParamsNode(exprNode, paramsTNodes);
    }

    @Override
    public String convertToJott() {
        if(exprNode == null && paramsTNodes == null) {
            return "";
        } else {
            StringBuilder jottCode = new StringBuilder(exprNode.convertToJott());
            for (ParamsTNode paramsTNode : paramsTNodes) {
                jottCode.append(paramsTNode.convertToJott());
            }
            return jottCode.toString();
        }
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
        this.exprNode.validateTree();
        for(ParamsTNode paramsT : this.paramsTNodes){
            paramsT.validateTree();
        }
        return true;
    }
    
}
