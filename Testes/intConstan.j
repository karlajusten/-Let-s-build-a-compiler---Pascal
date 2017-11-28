.class public intConstant
.super java/lang/Object


.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
ldc 42
istore 0
  getstatic java/lang/System/out Ljava/io/PrintStream;
iload 0
  invokevirtual java/io/PrintStream/println(I)V


  return
  
.end method