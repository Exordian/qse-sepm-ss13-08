package at.ac.tuwien.sepm.service.semesterPlanning;

import static org.junit.Assert.*;

import java.util.ArrayList;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.semesterPlaning.DependenceTree;
import org.junit.Before;
import org.junit.Test;


public class DependenceTreeTest {
	private DependenceTree tree;
	private MetaLVA lva0;
	private MetaLVA lva1;
	private MetaLVA lva2;
	private MetaLVA lva3;
	private MetaLVA lva4;
	@Before
	public void setUp(){
		lva0 = new MetaLVA();
		lva1 = new MetaLVA();
		lva2 = new MetaLVA();
		lva3 = new MetaLVA();
		lva4 = new MetaLVA();
		
		lva0.setNr("0");
		lva0.setPriority(2);
		lva0.setSemestersOffered(Semester.S);
		lva1.setNr("1");
		lva1.setPriority(3);
		lva1.setSemestersOffered(Semester.W_S);
		lva2.setNr("2");
		lva2.setPriority(4);
		lva2.setSemestersOffered(Semester.UNKNOWN);
		lva3.setNr("3");
		lva4.setNr("4");
		lva4.setSemestersOffered(Semester.W);
		ArrayList<MetaLVA> precursor1 = new ArrayList<MetaLVA>();
		ArrayList<MetaLVA> precursor0 = new ArrayList<MetaLVA>();
		precursor0.add(lva1);
		precursor1.add(lva2);
		precursor1.add(lva3);
		lva0.setPrecursor(precursor0);
		lva1.setPrecursor(precursor1);
		
		ArrayList<MetaLVA> pool = new ArrayList<MetaLVA>();
		pool.add(lva0);
		pool.add(lva1);
		pool.add(lva2);
		pool.add(lva4);
		
		tree = new DependenceTree(pool);
	}
	@Test
	public void testRoots() {
		ArrayList<MetaLVA> roots =tree.getRoots();
		assertTrue(roots.contains(lva2));
		assertTrue(roots.contains(lva4));
		assertTrue(roots.size()==2);
	}
	@Test
	public void testLeaves() {
		ArrayList<MetaLVA> leaves =tree.getLeaves();
		assertTrue(leaves.contains(lva0));
		assertTrue(leaves.contains(lva4));
		assertTrue(leaves.size()==2);
	}
	@Test
	public void testChangePriorityByParents() {
		tree.changePriorityByParents(0.2f);
		assertTrue(tree.getPriority(lva0)==2);
		assertTrue(tree.getPriority(lva1)==3+2*0.2f);
		assertTrue(tree.getPriority(lva2)==4+(3+2*0.2f)*0.2f);
	}
	@Test
	public void testChangePriorityBySemester() {
		tree.changePriorityBySemester(1);
		System.out.println(tree.getPriority(lva0));
		System.out.println(tree.getPriority(lva1));
		System.out.println(tree.getPriority(lva2));
		assertTrue(tree.getPriority(lva0)==3);
		assertTrue(tree.getPriority(lva1)==2);
		assertTrue(tree.getPriority(lva2)==4);
	}
	@Test
	public void testDefaultPriority() {
		assertTrue(tree.getPriority(lva0)==2);
		assertTrue(tree.getPriority(lva1)==3);
	}
	
}
