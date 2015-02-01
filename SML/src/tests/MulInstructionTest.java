package tests;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.Before;
import sml.Instruction;
import sml.Machine;
import sml.MulInstruction;

public class MulInstructionTest {

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
	public void MulConstructor() {
		Instruction mul = new MulInstruction("f0", "mul");
		System.out.println(mul.toString());
		String expected = "f0: mul 0 * 0 to 0";
		assertEquals(mul.toString(), expected);

		m.execute();
		m.getRegisters().setRegister(op1, 20);
		m.getRegisters().setRegister(op2, 10);
		Instruction mulCon = new MulInstruction("f0", result, op1, op2);

		String expected1 = "f0: mul 1 * 2 to 0";
		assertEquals(mulCon.toString(), expected1);

		int expected2 = 200;
		assertEquals(m.getRegisters().getRegister(1) * m.getRegisters().getRegister(2), expected2);

	}

	@Test
	public void toStringTest() {
		Instruction mul = new MulInstruction("mul", 0, 2, 5);
		String outcome = "mul: mul 2 * 5 to 0";
		assertEquals(mul.toString(), outcome);
	}

	@Test
	public void executeTest() {
		m.execute();
		m.getRegisters().setRegister(op1, 20);
		m.getRegisters().setRegister(op2, 10);
		int value1 = m.getRegisters().getRegister(op1);
		int value2 = m.getRegisters().getRegister(op2);
		m.getRegisters().setRegister(result, value1 * value2);
		assertEquals(m.getRegisters().getRegister(result), value1 * value2);

		MulInstruction mul = new MulInstruction("f0", result, op1, op2);
		mul.execute(m);
		assertEquals(m.getRegisters().getRegister(result), value1 * value2);
	}

}
