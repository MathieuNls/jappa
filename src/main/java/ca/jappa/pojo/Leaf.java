package ca.jappa.pojo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Leaf implements Comparable<Leaf>{
	
	private String className;
	private String methodName;
	private List<String> args;
	private List<Leaf> leaves;
	private int uuid;
	/**
	 * @param className
	 * @param methodName
	 */
	public Leaf(String className, String methodName, int uuid) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.leaves = new LinkedList<Leaf>();
		this.uuid = uuid;
	}

	/**
	 * @return
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * @param arg
	 */
	public void addArg(String arg){
		if(args == null ){
			args = new ArrayList<String>();
		}
		args.add(arg);
	}


	/**
	 * @return
	 */
	public List<String> getArgs() {
		return args;
	}

	/**
	 * @param args
	 */
	public void setArgs(List<String> args) {
		this.args = args;
	}
	
	public void addLeaf(Leaf leaf){
		this.leaves.add(leaf);
	}
	
	public List<Leaf> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leaf> leaves) {
		this.leaves = leaves;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toStringWithTabs("");
	}
	
	public String toStringWithTabs(String tabs){
		String r = tabs + className + "." + methodName + "("+ ((args == null) ? "" : this.argsString()) + ")\r\n";
		for(Leaf l : this.leaves){
			r += l.toStringWithTabs(tabs + "\t");
		}
		return r;
	}
	
	private String argsString(){
		String argsString = "";
		
		for (int i = 0; i < args.size(); i++) {
			argsString += args.get(i);
			if(i != args.size()-1){
				argsString += ";";
			}
		}
		return argsString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leaf other = (Leaf) obj;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		
		return true;
	}

	@Override
	public int compareTo(Leaf o) {
		return this.uuid - o.getUuid();
	}
	
	
}
