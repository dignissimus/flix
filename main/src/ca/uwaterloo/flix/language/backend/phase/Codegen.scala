package ca.uwaterloo.flix.language.backend.phase

import org.objectweb.asm._
import org.objectweb.asm.Opcodes._
import org.objectweb.asm.util.CheckClassAdapter

object Codegen {

  def genTestAsm(): Array[Byte] = {
    val cw = new ClassWriter(0)
    val cv = new CheckClassAdapter(cw)

    cv.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "ca/uwaterloo/flix/TestAsm", null, "java/lang/Object", null)

    // Constructor
    {
      val mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
      mv.visitCode()
      mv.visitVarInsn(ALOAD, 0)
      mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
      mv.visitInsn(RETURN)
      mv.visitMaxs(1, 1)
      mv.visitEnd()
    }
    // f
    {
      val mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "f", "()I", null, null)
      mv.visitCode()
      mv.visitInsn(ICONST_3)
      mv.visitInsn(ICONST_4)
      mv.visitInsn(IADD)
      mv.visitInsn(IRETURN)
      mv.visitMaxs(2, 0)
      mv.visitEnd()
    }
    cv.visitEnd()

    cw.toByteArray
  }

}

class Codegen {
  private val classWriter = new ClassWriter(0)
  private val visitor = new CheckClassAdapter(classWriter)

  lazy val bytecode: Array[Byte] = {
    // Initialize the visitor to create "public class ca.uwaterloo.flix.TestAsm extends java.lang.Object"
    // with no generic types and no interfaces
    visitor.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "ca/uwaterloo/flix/TestAsm", null, "java/lang/Object", null)

    // Generate the constructor for the class
    {
      val mv = visitor.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
      mv.visitCode()
      mv.visitVarInsn(ALOAD, 0)
      mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
      mv.visitInsn(RETURN)
      mv.visitMaxs(1, 1)
      mv.visitEnd()
    }

    // TODO: Generate code for each of the methods

    // Finish the traversal and convert to a byte array
    visitor.visitEnd()
    classWriter.toByteArray
  }
}