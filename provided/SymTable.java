package provided;

import java.util.ArrayList;
import java.util.HashMap;

public class SymTable {
    // Format: <Variable Name, Variable Type>
    public HashMap<String, String> varSymTab;
    // Format: <Function Name, ArrayList<Function Return Type, Parameter Types*>>
    public HashMap<String, ArrayList<String>> funcSymTab;

    public SymTable() {
        this.funcSymTab = new HashMap<>();
        this.varSymTab = new HashMap<>();
    }
}
