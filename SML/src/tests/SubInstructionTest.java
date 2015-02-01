package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sml.Instruction;
import sml.Machine;
import sml.SubInstruction;

public class SubInstructionTest 
{

	Machine m;
	int result;
	int op1;
	int op2;
	
	@Before
	public void setUpBefore() throws Exception 
	{
		m = new Machine();
		result = 0;
		op1 = 1;
		op2 = 2;
	}
	

	@Test
	public void subConstructor()
	{			
		Instruction sub = new SubInstruction("f0", "sub");
		String expected = "f0: sub 0 - 0 to 0";
		assertEquals(sub.toString(),expected);		
		
		m.execute();
		m.getRegisters().setRegister(op1, 20); 
		m.getRegisters().setRegister(op2, 10); 		
		Instruction subCon = new SubInstruction("f0", result, op1, op2);
		
		String expected1 = "f0: sub 1 - 2 to 0";
		assertEquals(subCon.toString(), expected1);
		
		int expected2 = 30;
		assertEquals(m.getRegisters().getRegister(1) + m.getRegisters().getRegister(2), expected2);
		
	}

	@Test
	public void toStringTest() 
	{
		Instruction sub = new SubInstruction("sub", 0, 2, 5);
		String outcome = "sub: sub 2 - 5 to 0";
		assertEquals(sub.toString(),outcome);		
	}
	
	
	@Test
	public void executeTest() 
	{
		m.execute();
		m.getRegisters().setRegister(op1, 20); 
		m.getRegisters().setRegister(op2, 10); 		
		int value1 = m.getRegisters().getRegister(op1);
		int value2 = m.getRegisters().getRegister(op2);		
		m.getRegisters().setRegister(result, value1 - value2); 	
		assertEquals(m.getRegisters().getRegister(result), value1 - value2);
		
		
		SubInstruction sub = new SubInstruction("f0", result, op1, op2);		
		sub.execute(m);
		assertEquals(m.getRegisters().getRegister(result), value1 - value2);
	}

	
}
