.class public whileStatementLess
.super java/lang/Object


.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  

ldc 0
istore 0
condWhile0:
iload 0
ldc 10
if_icmplt onTrue0
ldc 0
goto onFalse0
onTrue0:
ldc 1
onFalse0:
ifne trueWhile0
goto endWhile0
trueWhile0:
iload 0
ldc 1
iadd
istore 0
goto condWhile0
endWhile0:

  getstatic java/lang/System/out Ljava/io/PrintStream;
iload 0
  invokevirtual java/io/PrintStream/println(I)V


  return
  
.end method