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
		super(label, op); //calls the default superclass constructor
	}

	public BnzInstruction(String label, int op1, String label2) 
	{
		this(label, "bnz");  //calls the default constructor  with string "div"
		this.op1 = op1;
		this.label2 = label2;
	}

	@Override
	public void execute(Machine m) //checks the value of s1 - if !0 then L2 is to be execute next
	{
		int value1 = m.getRegisters().getRegister(op1);
		
		System.out.println("op1: " + value1);
		System.out.println("Label2: " + label2);
		if (value1 != 0)
		{
			//sets PC to the Label2 index
			int currentPC = m.getPc();
			//label has a indexof method which allows searching of label strings in the label array. 
			int labelCount = m.getLabels().indexOf(label2.trim());
			
			//System.out.println("Label2 is at position: " + labelCount);
			//checks to see if the label position matches that of the PC - if so then no need to execute as it will be done automatically
			if(currentPC != labelCount)
			{
				m.setPc(labelCount);
				System.out.println("Label2 is at position: " + labelCount);
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
