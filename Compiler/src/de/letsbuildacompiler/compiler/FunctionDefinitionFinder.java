package de.letsbuildacompiler.compiler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;

import de.letsbuildacompiler.parser.DemoBaseVisitor;
import de.letsbuildacompiler.parser.DemoParser.FunctionDefinitionContext;
import de.letsbulidacompiler.compiler.exceptions.FunctionAlreadyDefinedException;

public class FunctionDefinitionFinder {
	
	public static FunctionList findFunctions(ParseTree tree) {
		FunctionList definedFunctions = new FunctionList();
		 new DemoBaseVisitor<Void>() {
			 @Override
			public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
				 String functionName = ctx.funcName.getText();
				 int parameterCount = ctx.params.declarations.size();
				 if (definedFunctions.contains(functionName, parameterCount)){
					 throw new FunctionAlreadyDefinedException(ctx.funcName);
				 }
				 definedFunctions.add(functionName, parameterCount);
				 return null;
			}
		 }.visit(tree);
		 return definedFunctions;
	}
}
