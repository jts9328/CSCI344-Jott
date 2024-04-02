package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ParamsNode implements JottTree{

    private ExprNode exprNode;
    private ArrayList<ParamsTNode> paramsTNodes;
    private String funcId;

    /**
     * Grammar: < params > --> < expr > < params_t >*
     * 
     * @param exprNode      child node ExprNode 
     * @param paramsTNodes  arraylist of child nodes paramsTNode
     */
    public ParamsNode(ExprNode exprNode, ArrayList<ParamsTNode> paramsTNodes, String funcId) {
        this.exprNode = exprNode;
        this.paramsTNodes = paramsTNodes;
        this.funcId = funcId;
    }

    /**
     * Grammar: < params > --> ε
     */
    public ParamsNode() {
        this(null, null, null);
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      ParamsNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static ParamsNode parseParamsNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
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

        return new ParamsNode(exprNode, paramsTNodes, funcId);
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
    public boolean validateTree() throws SemanticErrorException {
        if(exprNode == null) return true;

        ArrayList<String> paramTypes = new ArrayList<String>(JottParser.symTable.funcSymTab.get(funcId));
        paramTypes.remove(0); // Remove the return type from the list
        
        // Check if the first param is correct
        if(exprNode.getResultingType() != paramTypes.remove(0)) {
            throw new SemanticErrorException("Semantic Error:\nIncorrect arguments for function " + funcId, exprNode.getToken());
        }

        exprNode.validateTree();

        // Check if the following params are correct
        for (ParamsTNode paramsTNode : paramsTNodes) {
            if(paramsTNode.getResultingType() != paramTypes.remove(0)) {
                throw new SemanticErrorException("Semantic Error:\nIncorrect arguments for function " + funcId, exprNode.getToken());
            }

            paramsTNode.validateTree();
        }

        // Function was not given all the params
        if(!paramTypes.isEmpty()) {
            throw new SemanticErrorException("Semantic Error:\nIncorrect arguments for function " + funcId, exprNode.getToken());
        }

        return true;
    }
    
    
}
