package sml;

/**
 * Duplicate of AddInstruction. 
 * Exchanged the + for a /, and amended necessary methods
 * @author mikieJ
 *
 */

public class DivInstruction extends Instruction 
{
	private int result;
	private int op1;
	private int op2;

	public DivInstruction(String label, String op) 
	{
		super(label, op); //calls the default superclass constructor
	}

	public DivInstruction(String label, int result, int op1, int op2) 
	{
		this(label, "div");  //calls the default constructor  with string "div"
		this.result = result;
		this.op1 = op1;
		this.op2 = op2;
	}
	
	//	Division takes place and puts it in the result register - cannot divide by Zero accounted for - 
	// sets the results register as zero rather than capturing the ArithmeticException
	@Override
	public void execute(Machine m) 
	{
		
			int value1 = m.getRegisters().getRegister(op1);
			int value2 = m.getRegisters().getRegister(op2);
		if (value2 != 0)
		{				
			m.getRegisters().setRegister(result, value1 / value2);
		}
		else
		{
			m.getRegisters().setRegister(result, 0);
			System.out.println("Cannot Divide by Zero");
		}
	}

	@Override
	public String toString() 
	{
		return super.toString() + " " + op1 + " / " + op2 + " to " + result;
	}
}
