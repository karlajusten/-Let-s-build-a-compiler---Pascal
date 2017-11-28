package de.letsbuildacompiler.compiler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import de.letsbuildacompiler.parser.DemoBaseVisitor;
import de.letsbuildacompiler.parser.DemoParser.AndContext;
import de.letsbuildacompiler.parser.DemoParser.AssignmentContext;
import de.letsbuildacompiler.parser.DemoParser.BlockConstDeclarationContext;
import de.letsbuildacompiler.parser.DemoParser.BlockVarDeclarationContext;
import de.letsbuildacompiler.parser.DemoParser.BranchContext;
import de.letsbuildacompiler.parser.DemoParser.CaseStatementContext;
import de.letsbuildacompiler.parser.DemoParser.ConstDeclarationContext;
import de.letsbuildacompiler.parser.DemoParser.ConstantContext;
import de.letsbuildacompiler.parser.DemoParser.DivContext;
import de.letsbuildacompiler.parser.DemoParser.FunctionCallContext;
import de.letsbuildacompiler.parser.DemoParser.FunctionDefinitionContext;
import de.letsbuildacompiler.parser.DemoParser.MainStatementContext;
import de.letsbuildacompiler.parser.DemoParser.MinusContext;
import de.letsbuildacompiler.parser.DemoParser.MultContext;
import de.letsbuildacompiler.parser.DemoParser.NumberContext;
import de.letsbuildacompiler.parser.DemoParser.OrContext;
import de.letsbuildacompiler.parser.DemoParser.PlusContext;
import de.letsbuildacompiler.parser.DemoParser.WriteContext;
import de.letsbuildacompiler.parser.DemoParser.WritelnContext;
import de.letsbuildacompiler.parser.DemoParser.ProgramContext;
import de.letsbuildacompiler.parser.DemoParser.RelationalContext;
import de.letsbuildacompiler.parser.DemoParser.StringContext;
import de.letsbuildacompiler.parser.DemoParser.VarDeclarationContext;
import de.letsbuildacompiler.parser.DemoParser.VariableContext;
import de.letsbuildacompiler.parser.DemoParser.WhileStatementContext;
import de.letsbulidacompiler.compiler.exceptions.ConstantAlreadyDefinedException;
import de.letsbulidacompiler.compiler.exceptions.UndeclaredVariableOrConstantException;
import de.letsbulidacompiler.compiler.exceptions.UndefinedFunctionException;
import de.letsbulidacompiler.compiler.exceptions.VariableAlreadyDefinedException;

public class MyVisitor extends DemoBaseVisitor<String>{
	
	private Map<String, Integer> variables = new HashMap<>();
	private Map<String, Integer> constants = new HashMap<>();
	private JvmStack jvmStack = new JvmStack();
	private final FunctionList definedFunctions;
	private int branchCounter = 0;
	private int whileCounter = 0;
	private int compareCount = 0;
	private int andCounter = 0;
	private int orCounter = 0;
	private String programName = "";
	
	public MyVisitor(FunctionList definedFunctions) {
		if (definedFunctions == null){
			throw new NullPointerException("definedFunctions");
		}
		this.definedFunctions = definedFunctions;
	}

	@Override
	public String visitWriteln(WritelnContext ctx) {
		String argumentInstructions = visit(ctx.argument);
		DataType type = jvmStack.pop();
		return "  getstatic java/lang/System/out Ljava/io/PrintStream;\n" + 
				 argumentInstructions + "\n" + 
				"  invokevirtual java/io/PrintStream/println(" +
				 type.getJvmType() + ")V\n";
		}
	
	@Override
	public String visitWrite(WriteContext ctx) {
		String argumentInstructions = visit(ctx.argument);
		DataType type = jvmStack.pop();
		return "  getstatic java/lang/System/out Ljava/io/PrintStream;\n" + 
				 argumentInstructions + "\n" + 
				"  invokevirtual java/io/PrintStream/print(" +
				 type.getJvmType() + ")V\n";
	}
	
	@Override
	public String visitPlus(PlusContext ctx) {
		String instructions = visitChildren(ctx) + "\n" +
			"iadd";
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		return instructions;
	}
	
	@Override
	public String visitMinus(MinusContext ctx) {
		String instructions = visitChildren(ctx) + "\n" +
				"isub";
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		return instructions;
	}
	
	@Override
	public String visitDiv(DivContext ctx) {
		String instructions = visitChildren(ctx) + "\n" +
				"idiv";
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		return instructions;
	}
	
	@Override
	public String visitMult(MultContext ctx) {
		String instructions = visitChildren(ctx) + "\n" +
				"imul";
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		return instructions;
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
		String instructions = visitChildren(ctx) + "\n" +
				   jumpInstruction + " onTrue" + compareNum + "\n" +
			 	   "ldc 0\n" +
			 	   "goto onFalse" + compareNum + "\n" +
			 	   "onTrue" + compareNum + ":\n" + 
			 	   "ldc 1\n" +
			 	   "onFalse"  + compareNum + ":"; 
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		return instructions;
	}
	
	@Override
	public String visitNumber(NumberContext ctx) {
		jvmStack.push(DataType.INT);
		return "ldc " +ctx.number.getText();
	}
	
	@Override
	public String visitString(StringContext ctx) {
		jvmStack.push(DataType.STRING);
		return "ldc " + ctx.txt.getText();
	}
	
	@Override
	public String visitVariable(VariableContext ctx) {
		jvmStack.push(DataType.INT);
		
		return "iload " + requireVariableorConstantIndex(ctx.varName);
	}
	
	@Override
	public String visitConstant(ConstantContext ctx) {
		jvmStack.push(DataType.INT);
		return "iload " + requireVariableorConstantIndex(ctx.constName);
		
	}
	
	@Override
	public String visitCaseStatement(CaseStatementContext ctx) {		
		int compareNum = compareCount;
		++compareCount;
		
		jvmStack.push(DataType.INT);
		String instructions = "iload " + requireVariableorConstantIndex(ctx.selector) + "\n";
				
		int i = 3 ;
		System.out.println("AKI!! " + programName + "\n");
		while(i < ctx.getChildCount()-1 ){
			System.out.println("child " + i + ": " + ctx.getChild(i).getText() + "\n");
			
			jvmStack.push(DataType.INT);
			
		    instructions += "ldc " + ctx.getChild(i).getText() + "\n" +
		    "if_icmpeq" + " onTrue" + compareNum + "case" + i + "\n" +
	 	    "goto onFalse" + compareNum + "case" + i + "\n" +
	 	    "onTrue" + compareNum + "case" + i + ":\n" +
		    visit(ctx.getChild(i+2)) + "\n" +
		    "goto EndCase" + compareNum  + "\n" +
	 	    "onFalse"  + compareNum + "case" + i + ":\n"; 
	 	   
		    i = i + 4;
		    jvmStack.pop();
		    
		}
		
		instructions += "goto EndCase" + compareNum  + ":\n"; 
		
		return instructions;
	}

	@Override
	public String visitWhileStatement(WhileStatementContext ctx) {
		String conditionInstructions = visit(ctx.conditionWhile);
		jvmStack.pop();
		String comandsInstruction = visit(ctx.comandsTorepeat);
		int whileNum = whileCounter;
		++whileCounter;
		//String whileReturn = 
		
		return "condWhile" + whileNum + ":\n" + 
		    conditionInstructions + "\n" +
			"ifne trueWhile" + whileNum + "\n" +
			"goto endWhile" + whileNum + "\n" +
			"trueWhile" + whileNum + ":\n" +
			comandsInstruction + "\n" +
			"goto condWhile" + whileNum + "\n" +
			"endWhile" + whileNum + ":\n";
	}
	
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
	@Override
	public String visitBranch(BranchContext ctx) {
		String conditionInstructions = visit(ctx.condition);
		jvmStack.pop();
		String onTrueInstructions = visit(ctx.onTrue);
		String onFalseInstructions = visit(ctx.onFalse);
		int branchNum = branchCounter;
		++branchCounter;
		
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
		instructions += "invokestatic " + programName + "/" + ctx.funcName.getText() + "(";
		instructions += stringRepeat("I", numberOfParameters);
		instructions += ")I";
		for(int i = 0; i < numberOfParameters; ++i ){
			jvmStack.pop();
		}
		jvmStack.push(DataType.INT);
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
		
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		
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
	
	
	/*
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
		
		jvmStack.pop();
		jvmStack.pop();
		jvmStack.push(DataType.INT);
		
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
	public String visitConstDeclaration(ConstDeclarationContext ctx) {
		if (constants.containsKey(ctx.constName.getText())) {
			throw new ConstantAlreadyDefinedException(ctx.constName);
		}
		constants.put(ctx.constName.getText(), constants.size());
		
		String instructions = visit(ctx.constValue) + "\n" +
				"istore " + requireVariableorConstantIndex(ctx.constName);
		jvmStack.pop();
		return instructions;
	}
	
	@Override
	public String visitAssignment(AssignmentContext ctx) {
		String instructions = visit(ctx.expr) + "\n" +
				"istore " + requireVariableorConstantIndex(ctx.varName);
		jvmStack.pop();
		return instructions;
	}
		
	@Override
	public String visitFunctionDefinition(FunctionDefinitionContext ctx) {
		Map<String, Integer> oldVariables = variables;
		JvmStack oldJvmStack = jvmStack;
		variables = new HashMap<>();
		jvmStack = new JvmStack();
		visit(ctx.params);
		String statementInstructions = "";
		if(ctx.bvarDec != null){
			statementInstructions = visit(ctx.bvarDec);
		}
		statementInstructions = visit(ctx.statements);
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
		jvmStack.pop();
		variables = oldVariables;
		jvmStack = oldJvmStack;
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
		programName = ctx.programName.getText();
		
		int i = 3;
		
		/* Have Constants Declarations*/
		if(ctx.getChild(i) instanceof BlockConstDeclarationContext){
			
			while(!ctx.getChild(i).getText().equals("VAR")
					&& !ctx.getChild(i).getText().equals("function")
					&& !ctx.getChild(i).getText().equals("BEGIN")){
				//AQUI: indo para assigment em vez de constant;
				mainCode = visit(ctx.getChild(i)) + "\n";
				i++;
			}
			i++;
		}
		
		/* Have Variables Declarations*/
		if(ctx.getChild(i) instanceof BlockVarDeclarationContext){
			
			while(!ctx.getChild(i).getText().equals("BEGIN") && !ctx.getChild(i).getText().equals("function")){
				mainCode = visit(ctx.getChild(i)) + "\n";
				i++;
			}
			i++;
		}
		
		if (ctx.getChild(i) instanceof FunctionDefinitionContext){ /* Don't have Variables Declarations, have functions*/ 
						
			while(!ctx.getChild(i).getText().equals("BEGIN")){
				functions = visit(ctx.getChild(i)) + "\n";
				i++;
			}
			
			i++;			
		}
		
		if (ctx.mainConstDeclaration == null && ctx.mainVarDeclaration == null && ctx.mainFunctionDeclaration == null){
			i = 4;
		}
		
		if (ctx.getChild(i) instanceof MainStatementContext){ 	/* Main block */
			while(i < ctx.getChildCount()-1) {
				ParseTree child = ctx.getChild(i);
				String instructions = visit(child);
				if (child instanceof MainStatementContext) {
					mainCode += instructions + "\n";
				}
				i++;
			}
		}
		
		String result = ".class public " + programName +"\n" +
				".super java/lang/Object\n" +
				"\n" + functions + "\n" +
				".method public static main([Ljava/lang/String;)V\n" + 
				"  .limit stack 100\n" + 
				"  .limit locals 100\n" + 
				"  \n" + 
				 mainCode + "\n" + 
				"  return\n" + 
				"  \n" + 
				".end method";
		
		try {
			createFile(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return result;
	}
	

	private int requireVariableorConstantIndex(Token NameToken) {
		Integer index = variables.get(NameToken.getText());		
		if (index == null) {
			index = constants.get(NameToken.getText());
			if (index == null){
				throw new UndeclaredVariableOrConstantException(NameToken);
			}
		}
		return index;
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
	
	public void createFile(String result) throws IOException{
		/*Create file:*/
		String nameProg = result.substring(14,result.indexOf(".", 14)-2);
		System.out.println("##Name = " + nameProg);
		

		FileWriter arq = new FileWriter("../Testes/" + nameProg + ".j");
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf(result);
		arq.close(); 
	}
	
}
