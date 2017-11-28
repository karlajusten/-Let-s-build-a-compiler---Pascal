package de.letsbulidacompiler.compiler.exceptions;

import org.antlr.v4.runtime.Token;

public class ConstantAlreadyDefinedException extends CompileException {

	private String constName;
	
	public ConstantAlreadyDefinedException(Token constantNameToken) {
		super(constantNameToken);
		constName = constantNameToken.getText();
	}
	
	@Override
	public String getMessage() {
		return line + ":" + column + " constant already defined: <" + constName + ">";
	}
	
}




