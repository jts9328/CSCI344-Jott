package nodes;

import provided.JottParser;
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

    public String getReturnType() {
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
        return idNode.getToken();
    }
    
    @Override
    public String convertToJott() {
        return "::" + idNode.convertToJott() + "[" + paramsNode.convertToJott() + "]";
    }

    @Override
    public String convertToJava(String className) {
        String funcId = idNode.convertToJava(className);

        switch(funcId) {
            case "print": return "System.out.println(" + paramsNode.convertToJava(className) + ")";
            case "concat":
                String[] data = paramsNode.getFirstTwoParamsJava(className);
                return data[0] + " + " + data[1];
            case "length":
                return paramsNode.getFirstParamJava(className) + ".length()";
            default: return "FunctionCallNode convertToJava error";
        }
    }

    @Override
    public String convertToPython(int tabs) {
        String funcId = idNode.convertToPython(tabs);
        String python = "";

        for(int i = 0; i < tabs; i++){
            python = python + ("\t");
        }
        switch(funcId) {
            case "print": return python + "print(" + paramsNode.convertToPython(tabs) + ")";
            case "concat":
                String[] data = paramsNode.getFirstTwoParamsPython(tabs);
                return python + data[0] + " + " + data[1];
            case "length":
                return python + "len(" + paramsNode.getFirstParamPython(tabs) + ")";
            default: return "FunctionCallNode convertToPython error";
        }
    }

    @Override
    public String convertToC() {
        String funcId = idNode.convertToC();

        switch(funcId) {
            case "print":
                String result = "printf(\"";
                String typeOfParam = paramsNode.getExpr().getReturnType();
                switch(typeOfParam) {
                    case "Double": result += "%f"; break;
                    case "String": result += "%s"; break;
                    case "Integer": result += "%d"; break;
                    case "Boolean": result += "%d"; break;
                    default: return "FunctionCallNode convertToJava error";
                }    
                result += "\", " + paramsNode.getFirstParamC() + ")";
                return result;

            case "concat":
                String[] data = paramsNode.getFirstTwoParamsC();
                return "strcat(" + data[0] + ", " + data[1] + ")";
            case "length":
                return "strlen(" + paramsNode.getFirstParamC() + ")";
            default: return "FunctionCallNode convertToJava error";
        }
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        // The function id provided does not exist
        if(!JottParser.symTable.funcSymTab.containsKey(idNode.getToken().getToken())) {
            throw new SemanticErrorException("Call to unknown function " + idNode.getToken().getToken(), idNode.getToken());
        }
        
        return paramsNode.validateTree();
    }

    @Override
    public boolean doesAllReturn() {
        return false;
    }

}
