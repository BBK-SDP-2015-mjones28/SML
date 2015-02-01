package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sml.BnzInstruction;
import sml.Instruction;
import sml.Machine;

public class BnzInstructionTest {

	Machine m;
	int result;
	int op1;
	int op2;
	String label;
	String label2;

	@Before
	public void setUpBefore() throws Exception {
		m = new Machine();
		result = 0;
		op1 = 1;
		op2 = 2;
		label = "f0";
		label2 = "f1";
		
		m.execute();
		m.getRegisters().setRegister(op1, 20);
		m.getRegisters().setRegister(op2, 10);	
		
	
		m.getLabels().addLabel(label);
		m.getLabels().addLabel(label2);
	}

	@Test
	public void executeTest()
	{

		assertEquals(m.getLabels().toString(), "(f0, f1)");		
		
		Instruction bnzCon = new BnzInstruction(label, result, label2);
		int currentPC = m.getPc();		
		assertEquals(currentPC, 0);
		int labelCount = m.getLabels().indexOf(label2.trim());
		assertEquals(labelCount, 1);		
		if(currentPC != labelCount)
		{
			m.setPc(labelCount);
		}
		else
		{
			System.out.println("PC and label same");
		}		
		assertEquals(m.getPc(), 1);

		
		
		labelCount = m.getLabels().indexOf(label.trim());
		assertEquals(labelCount, 0);
		if(currentPC != labelCount)
		{
			m.setPc(labelCount);
		}
		else
		{
			System.out.println("PC and label same");
		}		
		assertEquals(m.getPc(), 1);
		
		
	}
//
	@Test
	public void toStringTest() 
	{
		Instruction bnzCon = new BnzInstruction(label, result, label2);
		bnzCon.execute(m);
		String outCome = "f0: bnz 0, Label2: f1";
		assertEquals(bnzCon.toString(), outCome);
	}


}
