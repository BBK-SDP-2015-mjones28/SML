package sml;

/**
 * Duplicate of AddInstruction. 
 * Exchanged the + for a -, and amended necessary methods
 * @author mikieJ
 *
 */
public class SubInstruction extends Instruction 
{
	private int result;
	private int op1;
	private int op2;

	public SubInstruction(String label, String op) 
	{
		super(label, op); //calls the default superclass constructor
	}

	public SubInstruction(String label, int result, int op1, int op2) 
	{
		this(label, "sub");  //calls the default constructor  with string "Sub"
		this.result = result;
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public void execute(Machine m) //subtraction takes place and puts it in the result register
	{
		int value1 = m.getRegisters().getRegister(op1);
		int value2 = m.getRegisters().getRegister(op2);
		m.getRegisters().setRegister(result, value1 - value2); //changed '+' for '-'
	}

	@Override
	public String toString() 
	{ 
		return super.toString() + " " + op1 + " - " + op2 + " to " + result;  //changed '+' for '-'
	}
}
