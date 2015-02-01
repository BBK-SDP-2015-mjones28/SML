package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import sml.DivInstruction;
import sml.Instruction;
import sml.Machine;


public class DivInstructionTest
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
		Instruction divCon = new DivInstruction("f0", result, op1, op2);

		String expected1 = "f0: div 1 / 2 to 0";
		assertEquals(divCon.toString(), expected1);

		int expected2 = 2;
		assertEquals(m.getRegisters().getRegister(1) / m.getRegisters().getRegister(2), expected2);
		
		DivInstruction div = new DivInstruction("f0", "div");
		String expected = "f0: div 0 / 0 to 0";
		assertEquals(div.toString(), expected);

	}

	@Test
	public void toStringTest() 
	{
		Instruction div = new DivInstruction("div", 0, 2, 5);
		String outcome = "div: div 2 / 5 to 0";
		assertEquals(div.toString(), outcome);
	}

	@Test
	public void executeTest() {
		m.execute();
		m.getRegisters().setRegister(op1, 20);
		m.getRegisters().setRegister(op2, 10);
		int value1 = m.getRegisters().getRegister(op1);
		int value2 = m.getRegisters().getRegister(op2);
		m.getRegisters().setRegister(result, value1 / value2);
		assertEquals(m.getRegisters().getRegister(result), value1 / value2);

		DivInstruction div = new DivInstruction("f0", result, op1, op2);
		div.execute(m);
		assertEquals(m.getRegisters().getRegister(result), value1 / value2);
	}

}
