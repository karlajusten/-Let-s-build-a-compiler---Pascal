package de.letsbuildacompiler.compiler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import de.letsbuildacompiler.parser.DemoBaseVisitor;
import de.letsbuildacompiler.parser.DemoParser.AndContext;
import de.letsbuildacompiler.parser.DemoParser.AssignmentContext;
import de.letsbuildacompiler.parser.DemoParser.BranchContext;
import de.letsbuildacompiler.parser.DemoParser.DivContext;
import de.letsbuildacompiler.parser.DemoParser.FunctionCallContext;
import de.letsbuildacompiler.parser.DemoParser.FunctionDefinitionContext;
import de.letsbuildacompiler.parser.DemoParser.MainStatementContext;
import de.letsbuildacompiler.parser.DemoParser.MinusContext;
import de.letsbuildacompiler.parser.DemoParser.MultContext;
import de.letsbuildacompiler.parser.DemoParser.NumberContext;
import de.letsbuildacompiler.parser.DemoParser.OrContext;
import de.letsbuildacompiler.parser.DemoParser.PlusContext;
import de.letsbuildacompiler.parser.DemoParser.PrintlnContext;
import de.letsbuildacompiler.parser.DemoParser.ProgramContext;
import de.letsbuildacompiler.parser.DemoParser.RelationalContext;
import de.letsbuildacompiler.parser.DemoParser.VarDeclarationContext;
import de.letsbuildacompiler.parser.DemoParser.VariableContext;
import de.letsbulidacompiler.compiler.exceptions.UndeclaredVariableException;
import de.letsbulidacompiler.compiler.exceptions.UndefinedFunctionException;
import de.letsbulidacompiler.compiler.exceptions.VariableAlreadyDefinedException;

public class MyVisitor extends DemoBaseVisitor<String>{
	
	private Map<String, Integer> variables = new HashMap<>();
	private final FunctionList definedFunctions;
	private int branchCounter = 0;
	private int compareCount = 0;
	private int andCounter = 0;
	private int orCounter = 0;
	
	public MyVisitor(FunctionList definedFunctions) {
		if (definedFunctions == null){
			throw new NullPointerException("definedFunctions");
		}
		this.definedFunctions = definedFunctions;
	}

	@Override
	public String visitPrintln(PrintlnContext ctx) {
		return "  getstatic java/lang/System/out Ljava/io/PrintStream;\n" + 
				 visit(ctx.argument) + "\n" + 
				"  invokevirtual java/io/PrintStream/println(I)V\n";
	}
	
	@Override
	public String visitPlus(PlusContext ctx) {
		return visitChildren(ctx) + "\n" +
			"iadd";
	}
	
	@Override
	public String visitMinus(MinusContext ctx) {
		return visitChildren(ctx) + "\n" +
				"isub";
	}
	
	@Override
	public String visitDiv(DivContext ctx) {
		return visitChildren(ctx) + "\n" +
				"idiv";
	}
	
	@Override
	public String visitMult(MultContext ctx) {
		return visitChildren(ctx) + "\n" +
				"imul";
	}
	
	@Override
	public String visitRelational(RelationalContext ctx) {
		int compareNum = compareCount;
		++compareCount;
		String jumpInstruction;
		switch(ctx.operator.getText()){
		case "<":
			jumpInstruction = "if_icmplt";
			break;
		case "<=":
			jumpInstruction = "if_icmple";
			break;
		case ">":
			jumpInstruction = "if_icmpgt";
			break;
		case ">=":
			jumpInstruction = "if_icmpge";
			break;
		default:
			throw new IllegalArgumentException("Unkown operator: " + ctx.operator.getText());
		}
		return visitChildren(ctx) + "\n" +
			   jumpInstruction + " onTrue" + compareNum + "\n" +
		 	   "ldc 0\n" +
		 	   "goto onFalse" + compareNum + "\n" +
		 	   "onTrue" + compareNum + ":\n" + 
		 	   "ldc 1\n" +
		 	   "onFalse"  + compareNum + ":"; 
	}
	
	@Override
	public String visitNumber(NumberContext ctx) {
		return "ldc " +ctx.number.getText();
	}
	
	@Override
	public String visitVariable(VariableContext ctx) {
		return "iload " + requireVariableIndex(ctx.varName);
	}
		
	@Override
	public String visitBranch(BranchContext ctx) {
		String conditionInstructions = visit(ctx.condition);
		String onTrueInstructions = visit(ctx.onTrue);
		String onFalseInstructions = visit(ctx.onFalse);
		int branchNum = branchCounter;
		++branchCounter;
		
		/* 
		 * Structure Example:
		 * 	ldc 0
		 * 	ifne true
		 * 	ldc 81
		 * 	goto endif
		 * true: 
		 *  ldc 42
		 * endif:
		 *  invokestatic Demo/print(i)V
		 */
		return conditionInstructions + "\n" +
			"ifne ifTrue" + branchNum + "\n" +
			onFalseInstructions + "\n" +
			"goto endIf" + branchNum + "\n" +
			"ifTrue" + branchNum + ":\n" +
			onTrueInstructions + "\n" +
			"endIf" + branchNum + ":\n";

	}
	
	@Override
	public String visitFunctionCall(FunctionCallContext ctx) {
		int numberOfParameters = ctx.arguments.expressions.size();
		if (!definedFunctions.contains(ctx.funcName.getText(), numberOfParameters)){
			throw new UndefinedFunctionException(ctx.funcName);
		}
			
		String instructions = "";
		String argumentsInstructions = visit(ctx.arguments);
		if (argumentsInstructions != null) {
			instructions += argumentsInstructions + '\n';
		}
		instructions += "invokestatic HelloWorld/" + ctx.funcName.getText() + "(";
		instructions += stringRepeat("I", numberOfParameters);
		instructions += ")I";
		return instructions;
	}
	
	/*
	 * ldc a
	 * ifeq onAndFalse
	 * ldc b
	 * ifeq onAndFalse
	 * ldc 1
	 * goto andEnd
	 * onAndFalse:
	 * ldc 0
	 * andEnd:
	 */
	@Override
	public String visitAnd(AndContext ctx) {
		String left = visit(ctx.left);
		String right = visit(ctx.right);
		int andNum = andCounter;
		++andCounter;
		
		return  left + "\n" + 
				"ifeq onAndFalse" + andNum + "\n" + 
				right + "\n" + 
				"ifeq onAndFalse" + andNum + "\n" + 
				"ldc 1\n" + 
				"goto andEnd" + andNum + "\n" + 
				"onAndFalse" + andNum + ":\n" + 
				"ldc 0\n" + 
				"andEnd" + andNum + ":"; 
	}
	
	
	/**
	 * ldc a
		ifne onOrTrue
		ldc b
		ifne onOrTrue
		ldc 0
		goto orEnd
		onOrTrue: 
		ldc1
		orEnd:
	 */		
	@Override
	public String visitOr(OrContext ctx) {
		String left = visit(ctx.left);
		String right = visit(ctx.right);
		int orNum = orCounter ;
		++orCounter;
		
		return left + "\n" + 
				"ifne onOrTrue" + orNum + "\n" + 
				right + "\n" + 
				"ifne onOrTrue" + orNum + "\n" + 
				"ldc 0\n" + 
				"goto orEnd" + orNum + "\n" + 
				"onOrTrue" + orNum + ": \n" + 
				"ldc 1\n" + 
				"orEnd" + orNum + ":";
	}
	
	@Override
	public String visitVarDeclaration(VarDeclarationContext ctx) {
		if (variables.containsKey(ctx.varName.getText())) {
			throw new VariableAlreadyDefinedException(ctx.varName);
		}
		variables.put(ctx.varName.getText(), variables.size());
		return "";
	}
	
	@Override
	public String visitAssignment(AssignmentContext ctx) {
		return visit(ctx.expr) + "\n" +
				"istore " + requireVariableIndex(ctx.varName);
	}
		
	@Override
	public String visitFunctionDefinition(FunctionDefinitionContext ctx) {
		Map<String, Integer> oldVariables = variables;
		variables = new HashMap<>();
		visit(ctx.params);
		String statementInstructions = visit(ctx.statements);
		String result = ".method public static " + ctx.funcName.getText() + "(";
		int numberOfParameters = ctx.params.declarations.size();
		result += stringRepeat("I", numberOfParameters);
		result += ")I\n" +
				".limit locals 100\n" +
				".limit stack 100\n" +
				(statementInstructions == null ? "" : statementInstructions + "\n") +
				visit(ctx.returnValue) + "\n" +
				"ireturn\n" +
				".end method";
		variables = oldVariables;
		return result;
	}
	
	private String stringRepeat(String string, int count) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < count; ++i) {
			result.append(string);
		}
		return result.toString();
	}

	@Override
	public String visitProgram(ProgramContext ctx) {
		String mainCode = "";
		String functions = "";
		for(int i = 0; i < ctx.getChildCount(); ++i) {
			ParseTree child = ctx.getChild(i);
			String instructions = visit(child);
			if (child instanceof MainStatementContext) {
				mainCode += instructions + "\n";
			} else {
				functions += instructions + "\n";
			}
		}
		return functions + "\n" +
		".method public static main([Ljava/lang/String;)V\n" + 
		"  .limit stack 100\n" + 
		"  .limit locals 100\n" + 
		"  \n" + 
		 mainCode + "\n" + 
		"  return\n" + 
		"  \n" + 
		".end method";
	}
	
	private int requireVariableIndex(Token varNameToken) {
		Integer varIndex = variables.get(varNameToken.getText());
		if (varIndex == null) {
			throw new UndeclaredVariableException(varNameToken);
		}
		return varIndex;
	}
	
	@Override
	protected String aggregateResult(String aggregate, String nextResult) {
		if (aggregate == null){
			return nextResult;
		}
		if (nextResult == null){
			return aggregate;
		}
		return aggregate + "\n" + nextResult;
	}
	
}
