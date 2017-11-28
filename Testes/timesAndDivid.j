.class public timesAndDivide
.super java/lang/Object


.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
  getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 8
ldc 2
idiv
ldc 4
imul
  invokevirtual java/io/PrintStream/println(I)V


  return
  
.end method