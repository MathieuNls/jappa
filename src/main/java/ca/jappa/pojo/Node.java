package ca.jappa.pojo;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String className;
	private String methodName;
	private List<String> args;
	
	/**
	 * @param className
	 * @param methodName
	 */
	public Node(String className, String methodName) {
		super();
		this.className = className;
		this.methodName = methodName;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [className=" + className + ", methodName=" + methodName
				+ ", args=" + args + "]";
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
		Node other = (Node) obj;
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
	
	
}
