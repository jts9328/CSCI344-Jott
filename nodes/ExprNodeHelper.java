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