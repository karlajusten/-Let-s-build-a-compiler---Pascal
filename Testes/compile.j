.class public compiler
.super java/lang/Object


.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
  getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "hello world"
  invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V


  return
  
.end method