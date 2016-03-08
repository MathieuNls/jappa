package ca.jappa.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Graph {
	
	Map<Node, Set<Node>> nodes;

	/**
	 * 
	 */
	public Graph(){
		nodes = new HashMap<Node, Set<Node>>();
	}
	
	/**
	 * @param node
	 */
	public void addNode(Node node){
		if(!nodes.containsKey(node)){
			nodes.put(node, new HashSet<Node>());
		}
	}
	
	/**
	 * @param from
	 * @param to
	 */
	public void addNeighbour(Node from, Node to){
		this.addNode(from);
		nodes.get(from).add(to);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for(Entry<Node, Set<Node>> entry : nodes.entrySet()){
			
			stringBuilder.append(entry.getKey().getClassName());
			stringBuilder.append(".");
			stringBuilder.append(entry.getKey().getMethodName());
			stringBuilder.append("{");
			for(Node node : entry.getValue()){
				stringBuilder.append(node.getClassName());
				stringBuilder.append(".");
				stringBuilder.append(entry.getKey().getMethodName());
				stringBuilder.append(";");
			}
			stringBuilder.append("}");
			stringBuilder.append(";");
		}
		
		return stringBuilder.toString();
	}
	
	

}
