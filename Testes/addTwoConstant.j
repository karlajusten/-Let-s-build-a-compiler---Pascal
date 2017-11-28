.class public addTwoConstants
.super java/lang/Object


.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
ldc 2
istore 0
ldc 5
istore 1
  getstatic java/lang/System/out Ljava/io/PrintStream;
iload 0
iload 1
iadd
  invokevirtual java/io/PrintStream/println(I)V


  return
  
.end method