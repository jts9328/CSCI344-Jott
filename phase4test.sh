#!/bin/bash

# Function to compile Java files in a directory
compile_java_files() {
    directory=$1
    echo "Compiling Java files in $directory"
    find "$directory" -name "*.java" ! -name "output.java" > temp.txt
    javac @temp.txt
    rm temp.txt
}

# Compile Java files in exceptions folder
compile_java_files "exceptions"

# Compile Java files in provided folder
compile_java_files "provided"

# Compile Java files in nodes folder
compile_java_files "nodes"

# Iterate over each Java file in phase3tests folder and run Jott.java with it
for test_file in $(find phase3testcases -name "*.jott")
do
    echo -e "\n---------$test_file--------"
    java_output=$(java provided/Jott "$test_file" provided/output.java Java 2>&1)

    # Check if error occurred in the output
    if [[ $java_output == *"Error"* ]]; then
        echo "Semantic or Syntax Error occurred while running $test_file"
        continue
    fi

    javac provided/output.java

    if [ $? -ne 0 ]; then
        echo "Output.java failed to compile exiting."
        exit 1
    fi

    java provided/output
done