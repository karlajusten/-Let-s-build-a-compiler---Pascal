grammar Demo;

program: 'program' programName=IDENTIFIER ';'  
		 mainConstDeclaration=blockConstDeclaration?
		 mainVarDeclaration=blockVarDeclaration?					
		 mainFunctionDeclaration=functionDefinition*  				    
		 'BEGIN'
		 		programPart
		 'END.'
		 ;

programPart: statement+      	  #MainStatement
		   ;

statement: writeln ';' 
         | assignment ';' 
         | branch
         | write ';' 
         | whileStatement ';'
         | caseStatement
         ;

caseStatement: 'CASE' selector=IDENTIFIER 'OF' 
					(target=NUMBER ': BEGIN' doTarget=statement+ 'END;')+
					('ELSE BEGIN' doElse=statement 'END;')?
				'END;'
				;

whileStatement: 'while'  conditionWhile=expression 'do' 'BEGIN' comandsTorepeat=block 'END;' ;

branch: 'if' '(' condition=expression ') then' 'BEGIN' onTrue=block 'END' 'else' 'BEGIN' onFalse=block 'END;'
      ;

block: statement*  ;

expression: left=expression '/' right=expression #Div
		  | left=expression '*' right=expression #Mult
          | left=expression '-' right=expression #Minus
          | left=expression '+' right=expression #Plus
          | left=expression operator=('<' | '<=' | '>' | '>=') right=expression #Relational
		  | left=expression '&&' right=expression #And
		  | left=expression '||' right=expression #Or
		  | number=NUMBER  #Number
		  | txt=STRING #String
		  | varName=IDENTIFIER #Variable
		  | constName=IDENTIFIER #Constant
		  | functionCall #funcCallExpression
		  ;

blockVarDeclaration: 'VAR' (varDeclaration ';')+ ;
blockConstDeclaration: 'CONST' (constDeclaration ';')+;

varDeclaration: varName=IDENTIFIER ':''int' ;
constDeclaration: constName=IDENTIFIER '=' constValue=NUMBER;

assignment: varName=IDENTIFIER '=' expr=expression;

writeln: 'writeln(' argument=expression ')';

write: 'write(' argument=expression ')';


functionDefinition: 'function' funcName=IDENTIFIER '(' params=parameterDeclaration '): int;' bconstDec=blockConstDeclaration? bvarDec=blockVarDeclaration? 'BEGIN' statements=statementList 'return' returnValue=expression ';' 'END;' ;

parameterDeclaration: declarations+=varDeclaration ( ',' declarations+=varDeclaration)*
					| 
					;

statementList: statement* ;

functionCall: funcName=IDENTIFIER '(' arguments=expressionList ')' ;

expressionList: expressions+=expression ( ',' expressions+=expression)*
			  |
			  ;

IDENTIFIER: [a-zA-Z] [a-zA-Z0-9]* ;
NUMBER: [0-9]+;
COMMENT: '{' .*? '}' -> skip;

WHITESPACE: [ \t\n\r]+ -> skip;
STRING: '"' .*? '"' ;
