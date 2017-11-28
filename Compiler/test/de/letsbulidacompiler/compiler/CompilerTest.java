package de.letsbulidacompiler.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.letsbuildacompiler.compiler.Main;
import de.letsbulidacompiler.compiler.exceptions.ConstantAlreadyDefinedException;
import de.letsbulidacompiler.compiler.exceptions.FunctionAlreadyDefinedException;
import de.letsbulidacompiler.compiler.exceptions.UndeclaredVariableOrConstantException;
import de.letsbulidacompiler.compiler.exceptions.UndefinedFunctionException;
import de.letsbulidacompiler.compiler.exceptions.VariableAlreadyDefinedException;
import jasmin.ClassFile;

public class CompilerTest {
  private Path tempDir;
  
  @BeforeMethod
  public void creatTempDir() throws IOException {
	  tempDir = Files.createTempDirectory("compilerTest");
  }
  
  @AfterMethod
  public void deleteTempDir() {
	  deleteRecursive(tempDir.toFile());
  }
  
  private void deleteRecursive(File file) {
	if (file.isDirectory()) {
		for(File child : file.listFiles()) {
			deleteRecursive(child);
		}
	}
	if (!file.delete()) {
		throw new Error("Could not delete file <" + file + ">");
	}
  }

  @Test (dataProvider = "provide_code_expectedText")
  public void runningCode_outputExpectedText(
		  String description,
		  String code, 
		  String expectedText) throws Exception {
	  // execution
	  String actualOutput = compileAndRun(code);
	  
	  //evaluation
	  Assert.assertEquals(actualOutput, expectedText);
	  
  }
  
  	@Test(expectedExceptions = UndeclaredVariableOrConstantException.class,
			expectedExceptionsMessageRegExp = "1:32 undeclared variable <x>")
	public void compilingCode_throwsUndeclaredVariableException_ifReadingUndefinedVariable() throws Exception {
		// execution
		compileAndRun("program compiler; BEGIN writeln(x); END.");
		
		// evaluation performed by expected exception
	}
  
  	@Test(expectedExceptions = UndeclaredVariableOrConstantException.class,
			expectedExceptionsMessageRegExp = "1:24 undeclared variable <x>")
	public void compilingCode_throwsUndeclaredVariableException_ifWrintingUndefinedVariable() throws Exception {
		// execution
		compileAndRun("program compiler; BEGIN x = 5; END.");
		
		// evaluation performed by expected exception
	}
  	
  	@Test(expectedExceptions = VariableAlreadyDefinedException.class,
			expectedExceptionsMessageRegExp = "2:0 variable already defined: <x>")
	public void compilingCode_throwsVariableAlreadyDefinedException_whenDefiningAlreadyDefinedVariable() throws Exception {
		// execution
		compileAndRun("program VariableAlreadyDefinedException; VAR x: int; " + System.lineSeparator() +
		              "x: int; BEGIN x = 0; END.");
		
		// evaluation performed by expected exception
	}
  	
  	@Test(expectedExceptions = ConstantAlreadyDefinedException.class,
			expectedExceptionsMessageRegExp = "2:0 constant already defined: <x>")
	public void compilingCode_throwsConstantAlreadyDefinedException_whenDefiningAlreadyDefinedConstant() throws Exception {
		// execution
		compileAndRun("program ConstantAlreadyDefinedException; CONST x = 10; " + System.lineSeparator() +
		              "x = 5; BEGIN writeln(x); END.");
		
		// evaluation performed by expected exception
	}
  	
  	@Test(expectedExceptions = UndefinedFunctionException.class,
			expectedExceptionsMessageRegExp = "1:32 call to undefined function: <someUndefinedFunction>")
	public void compilingCode_throwsUndefinedFunctionException_whenCallingUndefinedFunction() throws Exception {
		// execution
		compileAndRun("program compiler; BEGIN writeln(someUndefinedFunction()); END.");
		
		// evaluation performed by expected exception
	}
  	
  	@Test(expectedExceptions = FunctionAlreadyDefinedException.class,
			expectedExceptionsMessageRegExp = "3:9 function already defined: <x>")
	public void compilingCode_throwsFunctionAlreadyDefinedException_whenDefiningFunctionTwice() throws Exception {
		// execution
		compileAndRun("program compiler;\n" + 
				  "function x(): int; BEGIN return 42; END;\n" +
				  "function x(): int; BEGIN return 24; END;\n" + 
				  "BEGIN writeln(x()); END.");
		
		// evaluation performed by expected exception
	}
  
  	
  @DataProvider
  public Object[][] provide_code_expectedText() throws Exception {
		return new Object[][]{
			{"plus", 
				"program Plus; BEGIN writeln(1+2); END.", "3" + System.lineSeparator()},
			
			{"chained plus", 
					"program chainedPlus; BEGIN writeln(1+2+42); END.", "45" + System.lineSeparator()},
			
			{"multiple statements", 
						"program compiler; BEGIN writeln(1); writeln(2); END.",
				"1" + System.lineSeparator() +
				"2" + System.lineSeparator()},
			
			{"minus", 
					"program minus; BEGIN writeln(3-2); END.", "1" + System.lineSeparator()},
			
			{"times", 
					"program times; BEGIN writeln(2*3); END.", "6" + System.lineSeparator()},
			
			{"divide", 
					"program divide; BEGIN writeln(6/2); END.", "3" + System.lineSeparator()},
			
			{"divide and truncate", 
					"program divideAndTruncate; BEGIN writeln(7/2); END.", "3" + System.lineSeparator()},
			
			{"precedence times and divide", 
					"program timesAndDivide; BEGIN writeln(8/2*4); END.", "16" + System.lineSeparator()},
			
			{"precedence plus and times",
					"program plusAndTimes; BEGIN writeln(2+3*3); END.", "11" + System.lineSeparator()},
			
			{"precedence minus and times",
					"program minusAndTimes; BEGIN writeln(9-2*3); END.", "3" + System.lineSeparator()},
			
			{"precedence minus and plus",
					"program minusAndPlus; BEGIN writeln(8-2+5); END.", "11" + System.lineSeparator()},
			
			{"int variable",
					"program intVariable; VAR foo: int; BEGIN foo = 42; writeln(foo); END.", "42" + System.lineSeparator()},
			
			{"add var and constant parameter", 
					"program addVarAndConstantParameter; VAR foo: int; BEGIN foo = 42; writeln(foo+2); END.", "44" + System.lineSeparator()},
			
			{"add two vars parameter",
					"program addTwoVarsParameter; VAR a: int; b: int; BEGIN a = 2; b = 5; writeln(a+b); END.", "7" + System.lineSeparator()},
			
			{"return only function",
					"program returnOnlyFunction; function randomNumber(): int; BEGIN return 4; END; "
					+ "BEGIN writeln(randomNumber()); END.", "4" + System.lineSeparator()},
			
			example("function/simple_function", 
				"4" + System.lineSeparator()),
			
			example("function/int_parameters",
					"13" + System.lineSeparator()),
			
			example("branch/if_int_false",
					"42" + System.lineSeparator()),
			
			example("branch/if_int_true",
					"81" + System.lineSeparator()),
			
			{"lower than true",
					"program compiler; BEGIN writeln(1 < 2); END.", "1" + System.lineSeparator()}, 
			
			{"lower than false",
						"program compiler; BEGIN writeln(2 < 2); END.", "0" + System.lineSeparator()}, 
				
			{"lower than or equal true",
							"program compiler; BEGIN writeln(2 <= 2); END.", "1" + System.lineSeparator()}, 
					
			{"lower than or equal false",
						"program compiler; BEGIN writeln(3 <= 2); END.", "0" + System.lineSeparator()}, 
			
			{"greater than true",
							"program compiler; BEGIN writeln(3 > 2); END.", "1" + System.lineSeparator()}, 
					
			{"greater than false",
						"program compiler; BEGIN writeln(2 > 2); END.", "0" + System.lineSeparator()},
			
			{"greater than or equal true",
						"program compiler; BEGIN writeln(2 >= 2); END.", "1" + System.lineSeparator()}, 
					
			{"greater than or equal false",
						"program compiler; BEGIN writeln(1 >= 2); END.", "0" + System.lineSeparator()}, 
			

			{ "and true", "program compiler; BEGIN writeln(1 && 1); END.", "1" + System.lineSeparator()},
			{ "and left false", "program compiler; BEGIN writeln(0 && 1); END.", "0" + System.lineSeparator()},			
			{ "and right false", "program compiler; BEGIN writeln(1 && 0); END.", "0" + System.lineSeparator()},
						
			{ "or false", "program compiler; BEGIN writeln(0 || 0); END.", "0" + System.lineSeparator()},
			{ "or left true", "program compiler; BEGIN writeln(1 || 0); END.", "1" + System.lineSeparator()},			
			{ "or right true", "program compiler; BEGIN writeln(0 || 1); END.", "1" + System.lineSeparator()},
			
			example("operators/and-skip-right", "0" + System.lineSeparator() + "0" + System.lineSeparator()),
			
			example("operators/or-skip-right", "1" + System.lineSeparator() + "1" + System.lineSeparator()),
			
			{"write", "program compiler; BEGIN write(42); END.", "42"},
			
			{"write string literal", "program compiler; BEGIN write(\"hello world\"); END.", "hello world"},
		
			example("conditionalStructures/whileStatementLess",
					"10" + System.lineSeparator()),
			
			example("conditionalStructures/whileStatementLessEqual",
					"11" + System.lineSeparator()),
			
			{"int constante",
				"program intConstant; CONST bla = 42; BEGIN writeln(bla); END.", "42" + System.lineSeparator()},
		
			{"add const", 
				"program addConstant; CONST foo = 42; BEGIN writeln(foo+2); END.", "44" + System.lineSeparator()},
		
			{"add two consts",
				"program addTwoConstants; CONST a = 2; b = 5; BEGIN writeln(a+b); END.", "7" + System.lineSeparator()},
		
			{"first comment",
					"program fisrtComment; {1º Comentario} VAR foo: int; BEGIN foo = 42; writeln(foo); END.", "42" + System.lineSeparator()},
			
			{"second comment",
						"program secondComment; VAR foo: int; {Second Comentario} BEGIN foo = 42; writeln(foo); END.", "42" + System.lineSeparator()},
			
			{"third comment",
							"program thirdComment; VAR foo: int; BEGIN foo = 42; {Third \n Comentário} writeln(foo); END.", "42" + System.lineSeparator()},
				
						
			/*example("conditionalStructures/caseWithoutElse",
					"Hello Word" + System.lineSeparator()),
			
			example("conditionalStructures/caseWithElse",
					"7" + System.lineSeparator() + "Entrou no Primeiro alvo" + System.lineSeparator()),*/
			
		};
	}
  
  private static String[] example(String name, String expectedResult) throws Exception {
	  try(InputStream in = CompilerTest.class.getResourceAsStream("/examples/" + name + ".txt")){
		  if (in == null){
			  throw new IllegalArgumentException("No such example <" + name + ">");
		  }
		  String code = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
		  return new String[]{name, code, expectedResult};
	  }
  }


  private String compileAndRun(String code) throws Exception {
		code = Main.compile(new ANTLRInputStream(code));
		System.out.println(code);
		ClassFile classFile = new ClassFile();
		classFile.readJasmin(new StringReader(code), "", false);
		Path outputPath = tempDir.resolve(classFile.getClassName() + ".class");
		try(OutputStream output = Files.newOutputStream(outputPath)) {
			classFile.write(output);
		}
		return runJavaClass(tempDir, classFile.getClassName());
	  }

	  private String runJavaClass(Path dir, String className) throws Exception {
		Process process = Runtime.getRuntime().exec(new String[]{"java", "-cp", dir.toString(), className});
		try(InputStream in = process.getInputStream()) {
			return new Scanner(in).useDelimiter("\\A").next();
		}
	  }
}
