/*
 * Copyright (c) 2011 - Georgios Gousios <gousiosg@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gr.gousiosg.javacg.stat;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.bcel.generic.Type;

import ca.jappa.pojo.CallTree;
import ca.jappa.pojo.Leaf;

/**
 * The simplest of method visitors, prints any invoked method signature for all
 * method invocations.
 * 
 * Class copied with modifications from CJKM: http://www.spinellis.gr/sw/ckjm/
 */
public class MethodVisitor extends EmptyVisitor {

	private JavaClass javaClass;
	private MethodGen methodGen;
	private ConstantPoolGen cp;
	private InvokeInstruction invokeInstruction;
	private Leaf leaf;

	/**
	 * @param methodGen
	 * @param javaClass
	 */
	public MethodVisitor(MethodGen methodGen, JavaClass javaClass) {
		this.javaClass = javaClass;
		this.methodGen = methodGen;
		this.cp = this.methodGen.getConstantPool();
		
		this.leaf = new Leaf(javaClass.getClassName(),
				this.methodGen.getName(), CallTree.uuid++);
		
		for (Type t : this.methodGen.getArgumentTypes()) {
			this.leaf.addArg(t.toString());
		}
		
		JCallGraph.callTree.addLeaf(leaf);
	}

	/**
	 * 
	 */
	public void start() {
		if (this.methodGen.isAbstract() || this.methodGen.isNative())
			return;
		for (InstructionHandle ih = this.methodGen.getInstructionList()
				.getStart(); ih != null; ih = ih.getNext()) {
			Instruction i = ih.getInstruction();

			if (!visitInstruction(i))
				i.accept(this);
		}
	}

	/**
	 * @param i
	 * @return
	 */
	private boolean visitInstruction(Instruction i) {
		short opcode = i.getOpcode();

		return ((InstructionConstants.INSTRUCTIONS[opcode] != null)
				&& !(i instanceof ConstantPushInstruction) && !(i instanceof ReturnInstruction));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bcel.generic.EmptyVisitor#visitINVOKEVIRTUAL(org.apache.bcel
	 * .generic.INVOKEVIRTUAL)
	 */
	@Override
	public void visitINVOKEVIRTUAL(INVOKEVIRTUAL i) {
		addLeaf(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bcel.generic.EmptyVisitor#visitINVOKEINTERFACE(org.apache.
	 * bcel.generic.INVOKEINTERFACE)
	 */
	@Override
	public void visitINVOKEINTERFACE(INVOKEINTERFACE i) {
		addLeaf(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bcel.generic.EmptyVisitor#visitINVOKESPECIAL(org.apache.bcel
	 * .generic.INVOKESPECIAL)
	 */
	@Override
	public void visitINVOKESPECIAL(INVOKESPECIAL i) {
		addLeaf(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bcel.generic.EmptyVisitor#visitINVOKESTATIC(org.apache.bcel
	 * .generic.INVOKESTATIC)
	 */
	@Override
	public void visitINVOKESTATIC(INVOKESTATIC i) {
		addLeaf(i);
	}

	private void addLeaf(InvokeInstruction invokeInstruction) {
		this.invokeInstruction = invokeInstruction;
		System.out.println(this);
		
		Leaf child = new Leaf(invokeInstruction
				.getReferenceType(this.cp).toString(), invokeInstruction
				.getMethodName(this.cp), CallTree.uuid++);

		for (Type t : invokeInstruction.getArgumentTypes(cp)) {
			child.addArg(t.toString());
		}
		
		JCallGraph.callTree.addLeafToParent(leaf, child);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return "M:" + javaClass.getClassName() + ":" + this.methodGen.getName()
				+ "->" + invokeInstruction.getReferenceType(this.cp) + ":"
				+ invokeInstruction.getMethodName(this.cp);
	}

	public Leaf getLeaf() {
		return leaf;
	}

	public void setLeaf(Leaf leaf) {
		this.leaf = leaf;
	}

}
