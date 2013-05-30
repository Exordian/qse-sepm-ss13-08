package at.ac.tuwien.sepm.service.semesterPlanning;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class DependenceTree implements Iterable<MetaLVA>{
	
	
	ArrayList<Node> pool = new ArrayList<Node>();
	ArrayList<Node> roots = new ArrayList<Node>();
	ArrayList<Node> leaves= new ArrayList<Node>();
	HashMap<Integer,Node> nodes;
	ArrayList<Node> nodesList;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    
	public DependenceTree(List<MetaLVA> lvas){
		nodesList = new ArrayList<Node>();
		nodes = new HashMap<Integer,Node>(lvas.size()+(int)(lvas.size()*0.2));
		for(MetaLVA lva: lvas){
			Node tempNode =new Node(lva);
			nodes.put(new Integer(lva.getNr().replace(".","")), tempNode);
			nodesList.add(tempNode);
		}
		for(MetaLVA lva: lvas){
			Node tempNode=nodes.get(new Integer(lva.getNr().replace(".","")));
			List<MetaLVA> tempPres =tempNode.getLVA().getPrecursor();
			for(MetaLVA pre : tempPres){
				if(nodes.containsKey(new Integer(pre.getNr().replace(".","")))){
					tempNode.addChild(nodes.get(new Integer(pre.getNr().replace(".", ""))));
				}
			}
			if(tempNode.getChildren().isEmpty()){
				roots.add(tempNode);
			}
		} 	
		for(MetaLVA lva: lvas){
			Node tempNode=nodes.get(new Integer(lva.getNr().replace(".", "")));
			if(tempNode.getParents().isEmpty()){
				leaves.add(tempNode);
			}
		} 	
	}
	public ArrayList<MetaLVA> getRoots(){
		Collections.sort(roots);
		ArrayList<MetaLVA> toReturn = new ArrayList<MetaLVA>(roots.size());
		for(Node n:roots){
			toReturn.add(n.getLVA());
		}
		return toReturn;
	}
	public ArrayList<MetaLVA> getLeaves(){
		Collections.sort(leaves);
		ArrayList<MetaLVA> toReturn = new ArrayList<MetaLVA>(leaves.size());
		for(Node n:leaves){
			toReturn.add(n.getLVA());
		}
		return toReturn;
	}
	public void changePriorityByParents(float rate){
		for(Node n:roots){
			n.changePriorityByParents(rate);
		}
	}
	public void changePriorityBySemester(float delta){
		for(Node n:nodesList){
			logger.debug(n.getLVA());
			n.changePriorityBySemester(delta);
		}
	}
	public float getPriority(MetaLVA lva){
		return nodes.get(new Integer(lva.getNr().replace(".", ""))).getPriority();
	}
	private class Node implements Comparable<Node>{
		MetaLVA lva;
		float priority;
		ArrayList<Node> children = new ArrayList<Node>();
		ArrayList<Node> parents = new ArrayList<Node>();
		private Node(MetaLVA lva){
			this.lva=lva;
			this.priority=lva.getPriority();
		}
		public MetaLVA getLVA(){
			return lva;
		}
		private void addChild(Node other){
			children.add(other);
			other.parents.add(this);
		}
		private ArrayList<Node> getChildren(){
			return children;
		}
		private ArrayList<Node> getParents(){
			return parents;
		}
		public float changePriorityByParents(float rate){
			for(Node n:parents){
				priority+=n.changePriorityByParents(rate);
			}
			return priority*rate;
		}
		public void changePriorityBySemester(float delta){
			Semester temp = lva.getSemestersOffered();
			switch(temp){
			case S:
			case W:
				priority+=delta;
				break;
			case W_S:
				priority-=delta;
			}
		}
		public float getPriority(){
			return priority;
		}
		@Override
		public int compareTo(Node o) {
			return (int) ((priority - o.priority)*128);
		}
	}
	@Override
	public Iterator<MetaLVA> iterator() {
		return new MetaLVAIterator();
	}
	private class MetaLVAIterator implements Iterator<MetaLVA>{
		Iterator<Node> rootsIter;
		private MetaLVAIterator(){
			Collections.sort(roots);
			rootsIter = roots.iterator();
		}
		@Override
		public boolean hasNext() {
			return rootsIter.hasNext();
		}

		@Override
		public MetaLVA next() {
			return rootsIter.next().getLVA();
		}

		@Override
		public void remove() {
		}
		
	}
}
