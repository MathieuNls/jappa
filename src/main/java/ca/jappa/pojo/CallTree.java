package ca.jappa.pojo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class CallTree {

	LinkedHashMap<Leaf, List<Leaf>> leaves;

	public static int uuid = 0;
	/**
	 * 
	 */
	public CallTree() {
		leaves = new LinkedHashMap<Leaf, List<Leaf>>();
	}
	
	public void addLeaf(Leaf l){
		this.leaves.put(l, new ArrayList<Leaf>());
	}
	
	public void addLeafToParent(Leaf parent, Leaf child){
		this.leaves.get(parent).add(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		
		for(Leaf leaf : leaves.keySet()){
			recursivePrint(leaf, stringBuilder, "");
		}

		return stringBuilder.toString();
	}
	
	private void recursivePrint(Leaf leaf, StringBuilder stringBuilder, String tabs){
		
		stringBuilder.append(tabs+leaf.toString());
		
		if(leaves.containsKey(leaf)){
			for (Leaf child : leaves.get(leaf)) {
				recursivePrint(child, stringBuilder, tabs+"\t");
			}
		}
	}

}
