package provided;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.SemanticErrorException;

public class Jott {
    public static void main(String[] args) throws IOException {
        if(args.length == 3){
            // Run parser to convert to Jott
            ArrayList<Token> tokens = JottTokenizer.tokenize(args[0]);
            JottTree tree = JottParser.parse(tokens);

            String code = "";

            // Syntax Error
            if(tree == null) return;

            try {
                tree.validateTree();
            } catch (SemanticErrorException e) {
                e.printErrorMessage();
                return;
            }

            if(args[2].equals("C")){
                code = tree.convertToC();
            } else if(args[2].equals("Python")){
                code = tree.convertToPython(0);
            } else if(args[2].equals("Java")){
                code = tree.convertToJava(args[1]);
            } else if(args[2].equals("Jott")){
                code = tree.convertToJott(); 
            } else{
                // error
                return;
            }

            FileWriter writer = new FileWriter(args[1]);
            writer.write(code);
            writer.close();
        } else{
            // error
            return;
        }
    }
}
