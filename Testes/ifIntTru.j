.class public ifIntTrue
.super java/lang/Object


.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
ldc 1
ifne ifTrue0
  getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 42
  invokevirtual java/io/PrintStream/println(I)V

goto endIf0
ifTrue0:
  getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 81
  invokevirtual java/io/PrintStream/println(I)V

endIf0:


  return
  
.end method