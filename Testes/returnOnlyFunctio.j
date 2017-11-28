.class public returnOnlyFunction
.super java/lang/Object

.method public static randomNumber()I
.limit locals 100
.limit stack 100
ldc 4
ireturn
.end method

.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
  getstatic java/lang/System/out Ljava/io/PrintStream;
invokestatic returnOnlyFunction/randomNumber()I
  invokevirtual java/io/PrintStream/println(I)V


  return
  
.end method