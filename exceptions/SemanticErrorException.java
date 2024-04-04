package exceptions;

import provided.Token;

public class SemanticErrorException extends Exception {

    private Token token;

    /**
     * Custom exception for syntax errors, takes in a message and a token
     * so the line number and file name can be displayed
     * 
     * @param errorMessage  specific error message
     * @param token         token that error occured on
     */
    public SemanticErrorException(String errorMessage, Token token) {
        super(errorMessage);
        this.token = token;
    }

    /**
     * Print the appropriate error message
     */
    public void printErrorMessage() {
        if (token == null) {
            System.err.println("Semantic Error:\n" + getMessage());
            return;
        }
        System.err.println("Semantic Error:\n" + getMessage() + "\n" + token.getFilename() + ":" + token.getLineNum());
    }
}
