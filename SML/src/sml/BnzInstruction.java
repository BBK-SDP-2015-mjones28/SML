package sml;

/**
 * Duplicate of AddInstruction. 
 * Amended class to allow for searching of a second label passed to the object as a parameter
 * @author mikieJ
 *
 */

public class BnzInstruction extends Instruction 
{
	private int op1;
	private String label2;

	public BnzInstruction(String label, String op) 
	{
		super(label, op); 
	}

	public BnzInstruction(String label, int op1, String label2) 
	{
		this(label, "bnz");  
		this.op1 = op1;
		this.label2 = label2;
	}

	@Override
	public void execute(Machine m) 
	{
		int value1 = m.getRegisters().getRegister(op1);
		
		
		if (value1 != 0)
		{
	
			int currentPC = m.getPc();			
			int labelCount = m.getLabels().indexOf(label2.trim());
			
			//System.out.println("Label2 is at position: " + labelCount);
			//checks to see if the label position matches that of the PC - if so then no need to execute as it will be done automatically
			if(currentPC != labelCount)
			{
				m.setPc(labelCount);
			}
			else
			{
				System.out.println("PC and label same");
			}
		}
		
	}

	@Override
	public String toString() 
	{
		return super.toString() + " " + op1 + ", Label2: " + label2;
	}
}
