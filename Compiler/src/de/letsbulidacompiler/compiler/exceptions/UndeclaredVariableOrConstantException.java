package de.letsbulidacompiler.compiler.exceptions;

import org.antlr.v4.runtime.Token;

public class UndeclaredVariableOrConstantException extends CompileException {

	private String constName;
	
	public UndeclaredVariableOrConstantException(Token constNameToken) {
		super(constNameToken);
		constName = constNameToken.getText();
	}
	
	@Override
	public String getMessage() {
		return line + ":" + column + " undeclared variable <" + constName + ">";
	}
		
}
