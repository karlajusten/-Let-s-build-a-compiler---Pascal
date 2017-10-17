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
		DemoLexer lexer = new DemoLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DemoParser parser = new DemoParser(tokens);
		
		ParseTree tree = parser.addition();
		System.out.println(createJasminFile(new MyVisitor().visit(tree)));

	}
	
	public static String createJasminFile( String instructions){
		return ".class public HelloWorld\n" +
				".super java/lang/Object\n" +
				".method public static main([Ljava/lang/String;)V\n" +
				"	.limit stack 100\n" +
				"	.limit locals 100\n" +
				"\n" +
				"	getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
				instructions + "\n" +
				"	invokevirtual java/io/PrintStream/println(I)V\n" +
				"	return \n" +
				"	\n" +
				".end method";
		
		
	}

}
