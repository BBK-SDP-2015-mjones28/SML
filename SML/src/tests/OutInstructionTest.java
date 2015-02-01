package tests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import sml.Instruction;
import sml.Machine;
import sml.OutInstruction;

public class OutInstructionTest
{

	Machine m;
	int result;
	int op1;
	int op2;

	@Before
	public void setUpBefore() throws Exception {
		m = new Machine();
		result = 0;
		op1 = 1;
		op2 = 2;
	}

	@Test
	public void DivConstructor()
	{
		m.execute();
		m.getRegisters().setRegister(op1, 20);
		m.getRegisters().setRegister(op2, 10);
		Instruction outCon = new OutInstruction("f0", 2);		
		String expected1 = "f0: out 2";
		assertEquals(outCon.toString(), expected1);
	}


	@Test
	public void executeTest() 
	{
		m.execute();
		m.getRegisters().setRegister(op1, 20);
		m.getRegisters().setRegister(op2, 10);
		String label = "f0";
		OutInstruction out = new OutInstruction(label, op1);
		out.execute(m);
		assertEquals(m.getRegisters().getRegister(op1), 20);
		String expected = label + ": out " + op1;
		assertEquals(out.toString(), expected);		
	}

}
