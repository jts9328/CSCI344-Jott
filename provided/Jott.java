package provided;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.SemanticErrorException;

public class Jott {
    public static void main(String[] args) throws IOException {
        if(args.length == 3){
            if(args[2].equals("C")){
                // Run parser to convert to C
                throw new UnsupportedOperationException("Unimplemented C call");
            } else if(args[2].equals("Python")){
                // Run parser to convert to Python
                throw new UnsupportedOperationException("Unimplemented Python call");
            } else if(args[2].equals("Java")){
                // Run parser to convert to Java
                throw new UnsupportedOperationException("Unimplemented Java call");
            } else if(args[2].equals("Jott")){
                // Run parser to convert to Jott
                ArrayList<Token> tokens = JottTokenizer.tokenize(args[0]);
                JottTree tree = JottParser.parse(tokens);
                try {
                    tree.validateTree();
                } catch (SemanticErrorException e) {
                    e.printErrorMessage();
                    return;
                }
                
                String jottCode = tree.convertToJott();
                FileWriter writer = new FileWriter(args[1]);
                writer.write(jottCode);
                writer.close();
            } else{
                // error
                return;
            }
        } else{
            // error
            return;
        }
    }
}
