#!/bin/bash

# Check if command-line arguments are provided
if [ $# -eq 0 ]; then
    echo "No command-line arguments provided. Give java, c or python"
    exit 1
fi

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

if [ "$1" == "java" ]; then
    # Iterate over each Java file in phase3tests folder and run Jott.java with it
    for test_file in $(find phase3testcases -name "*.jott")
    do
        echo -e "\n---------$test_file--------"
        java_output=$(java provided/Jott "$test_file" provided/output.java Java 2>&1)

        echo -e "Jott.java output: "
        echo $java_output

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

        echo -e "Java output: "
        java provided/output
    done
elif [ "$1" == "c" ]; then
    # Iterate over each Java file in phase3tests folder and run Jott.java with it
    for test_file in $(find phase3testcases -name "*.jott")
    do
        echo -e "\n---------$test_file--------"
        java_output=$(java provided/Jott "$test_file" provided/output.c C 2>&1)

        echo -e "Jott.java output: "
        echo $java_output

        # Check if error occurred in the output
        if [[ $java_output == *"Error"* ]]; then
            echo "Semantic or Syntax Error occurred while running $test_file"
            continue
        fi

        gcc -o provided/output provided/output.c

        if [ $? -ne 0 ]; then
            echo "Output.c failed to compile exiting."
            exit 1
        fi

        echo -e "C output: "
        ./provided/output
    done
elif [ "$1" == "python" ]; then
    # Iterate over each Java file in phase3tests folder and run Jott.java with it
    for test_file in $(find phase3testcases -name "*.jott")
    do
        echo -e "\n---------$test_file--------"
        java_output=$(java provided/Jott "$test_file" provided/output.py Python 2>&1)

        echo -e "Jott.java output: "
        echo $java_output

        # Check if error occurred in the output
        if [[ $java_output == *"Error"* ]]; then
            echo "Semantic or Syntax Error occurred while running $test_file"
            continue
        fi

        echo -e "Python output: "
        python provided/output.py
        if [ $? -ne 0 ]; then
            echo "output.py failed"
            exit 1
        fi
    done
else
    echo "Give java, c or python as first argument"
fi
