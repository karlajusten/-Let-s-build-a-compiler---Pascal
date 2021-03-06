// https://www.youtube.com/playlist?list=PLOfFbVTfT2vbJ9qiw_6fWwBAmJAYV4iUm
// Reproduzido por Karla Aparecida Justen 2017.2

//
// Comand to compile the grammar on ANTLR4
// java -jar ../lib/antlr-4.7-complete.jar -package de.letsbuildacompiler.parser -o ../src/de/letsbuildacompiler/parser -no-listener -visitor Demo.g4

package de.letsbuildacompiler.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import de.letsbuildacompiler.parser.DemoLexer;
import de.letsbuildacompiler.parser.DemoParser;

public class Main {
	
	public static void main(String[] args) throws Exception {		
		ANTLRInputStream input = new ANTLRFileStream("code.demo");
		String result = compile(input);
		System.out.println(result);
		
	}
	
	public static String compile(ANTLRInputStream input){
		DemoLexer lexer = new DemoLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DemoParser parser = new DemoParser(tokens);
		
		ParseTree tree = parser.program();
		FunctionList definedFunctions = FunctionDefinitionFinder.findFunctions(tree);
		return createJasminFile(new MyVisitor(definedFunctions).visit(tree));
	}
	
	public static String createJasminFile( String instructions){
		return instructions;
	}

}
