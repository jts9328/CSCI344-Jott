package nodes;

import provided.JottTree;

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

    public static ElseNode parseElse(ArrayList<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            // TODO Throw exception instead of NULL
            return null; // No tokens to parse
        }

        Token token = tokens.get(0);

        //check if there is an else statement
        if (token.getToken().equal("Else")) {
            token = tokens.remove(0); //remove else token
            //parse body
            BodyNode body = BodyNode.parseBody(tokens);
            return ElseNode(body);
        } else {
            //no else return null else node
            return ElseNode();
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
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
