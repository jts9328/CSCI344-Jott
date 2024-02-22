package exceptions;

import provided.Token;

public class SyntaxErrorException extends Exception {

    private Token token;

    /**
     * Custom exception for syntax errors, takes in a message and a token
     * so the line number and file name can be displayed
     * 
     * @param errorMessage  specific error message
     * @param token         token that error occured on
     */
    public SyntaxErrorException(String errorMessage, Token token) {
        super(errorMessage);
        this.token = token;
    }

    public int getLineNumber() {
        return token.getLineNum();
    }

    public String getFilename() {
        return token.getFilename();
    }
}
