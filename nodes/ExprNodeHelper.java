package nodes;

// assist ExprNode Interface with two of it branches.
public class ExprNodeHelper implements ExprNode{
    private OperandNode first;
    private OpNode op;
    private OperandNode second;

    public ExprNodeHelper(OperandNode first,OpNode op, OperandNode second ){
        this.first = first;
        this.op = op;
        this.second = second;
    }

    public String getResultingType() {
        Token opToken = this.op.getToken();
        if (opToken.getTokenType() == TokenType.REL_OP) {
            return "Bool"
        }
        if (opToken.getTokenType() == TokenType.MATH_OP) {
            return this.first.getResultingType();
        }
    }

    public Token getToken() {
        return null;
    }

    @Override
    public String convertToJott() {
        return first.convertToJott() + op.convertToJott() + second.convertToJott();
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