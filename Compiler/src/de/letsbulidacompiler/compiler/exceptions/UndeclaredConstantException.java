package de.letsbulidacompiler.compiler.exceptions;

import org.antlr.v4.runtime.Token;

public class UndeclaredConstantException extends CompileException {
	
private String constName;
	
	public UndeclaredConstantException(Token constNameToken) {
		super(constNameToken);
		constName = constNameToken.getText();
	}
	
	@Override
	public String getMessage() {
		return line + ":" + column + " undeclared variable <" + constName + ">";
	}

}
