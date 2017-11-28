.class public intParameters
.super java/lang/Object

.method public static add(II)I
.limit locals 100
.limit stack 100
iload 0
iload 1
iadd
ireturn
.end method

.method public static main([Ljava/lang/String;)V
  .limit stack 100
  .limit locals 100
  
  getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 5
ldc 8
invokestatic intParameters/add(II)I
  invokevirtual java/io/PrintStream/println(I)V


  return
  
.end method