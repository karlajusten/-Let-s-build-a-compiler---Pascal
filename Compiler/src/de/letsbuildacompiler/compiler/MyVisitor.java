package de.letsbuildacompiler.compiler;

import java.util.HashMap;
import java.util.Map;

import de.letsbuildacompiler.parser.DemoBaseVisitor;
import de.letsbuildacompiler.parser.DemoParser.AssignmentContext;
import de.letsbuildacompiler.parser.DemoParser.DivContext;
import de.letsbuildacompiler.parser.DemoParser.MinusContext;
import de.letsbuildacompiler.parser.DemoParser.MultContext;
//import de.letsbuildacompiler.parser.DemoParser.AdditionContext;
import de.letsbuildacompiler.parser.DemoParser.NumberContext;
import de.letsbuildacompiler.parser.DemoParser.PlusContext;
import de.letsbuildacompiler.parser.DemoParser.PrintlnContext;
//import de.letsbuildacompiler.parser.DemoParser.ZahlContext;
import de.letsbuildacompiler.parser.DemoParser.VarDeclarationContext;
import de.letsbuildacompiler.parser.DemoParser.VariableContext;

public class MyVisitor extends DemoBaseVisitor<String>{
	
	private Map<String, Integer> variables = new HashMap<>();
	
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
	public String visitNumber(NumberContext ctx) {
		return "ldc " +ctx.number.getText();
	}
	
	@Override
	public String visitVarDeclaration(VarDeclarationContext ctx) {
		variables.put(ctx.varName.getText(), variables.size());
		return "";
	}
	
	@Override
	public String visitAssignment(AssignmentContext ctx) {
		return visit(ctx.expr) + "\n" +
				"istore " + variables.get(ctx.varName.getText());
	}
	
	@Override
	public String visitVariable(VariableContext ctx) {
		return "iload " + variables.get(ctx.varName.getText());
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
