package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class ElseNode implements JottTree{
    private BodyNode body;

    // Else Statement Constructor
    public ElseNode(BodyNode body) {
        this.body = body;
    }

    // Else Null Constructor
    public ElseNode() {
        this.body = null;
    }

    public static ElseNode parseElse(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", null);
        }

        Token token = tokens.get(0);

        //check if there is an else statement
        if (token.getToken().equals("Else")) {
            token = tokens.remove(0); //remove else token
            //check for left brace
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.L_BRACE) {
                throw new SyntaxErrorException("Expected L_BRACE, found: " + token.getTokenType(), token);
            }
            //parse body
            BodyNode body = BodyNode.parseBodyNode(tokens, funcId);
            //check for right brace
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.R_BRACE) {
                throw new SyntaxErrorException("Expected R_BRACE, found: " + token.getTokenType(), token);
            }
            return new ElseNode(body);
        } else {
            return new ElseNode();
        }
    }

    @Override
    public String convertToJott() {
        if (body != null) {
            StringBuilder jottCode = new StringBuilder();
            jottCode.append("Else {");
            jottCode.append(body.convertToJott()).append("}\n");
            return jottCode.toString();
        } else {
            return "";
        }
    }

    @Override
    public String convertToJava(String className) {
        if (body != null) {
            StringBuilder javaCode = new StringBuilder();
            javaCode.append("else {");
            javaCode.append(body.convertToJava(className)).append("}\n");
            return javaCode.toString();
        } else {
            return "";
        }
    }

    @Override
    public String convertToC() {
        if (body != null) {
            StringBuilder cCode = new StringBuilder();
            cCode.append("else {");
            cCode.append(body.convertToC()).append("}\n");
            return cCode.toString();
        } else {
            return "";
        }
    }

    @Override
    public String convertToPython(int tabs) {
        if (body != null) {
            StringBuilder pythonCode = new StringBuilder();
            for(int i = 0; i < tabs; i++){
                pythonCode = pythonCode.append("\t");
            }
            pythonCode.append("else: \n");
            pythonCode.append(body.convertToPython(tabs + 1)).append("\n");
            return pythonCode.toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean validateTree() throws SemanticErrorException{
        if(body == null) {
            return true;
        }
        
        this.body.validateTree();
        return true;
    }

    public boolean doesAllReturn() {
        if(body != null) return body.doesReturn();

        return false;
    }
    
}
