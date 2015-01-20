package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";

	public Translator(String fileName) 
	{
		this.fileName = SRC + "/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) 
	{

		try (Scanner sc = new Scanner(new File(fileName))) 
		{
			// Scanner attached to the file chosen by the user
			labels = lab;		
			labels.reset();   
			program = prog; 	
			program.clear();	

			try {
					line = sc.nextLine();		
				}
				catch (NoSuchElementException ioE) 
				{
					return false;
				}

			// Each iteration processes line and reads the next line into line
			while (line != null) 
			{
				
				String label = scan();  
										
				if (label.length() > 0) 
				{
					Instruction ins = getInstruction(label);  
					if (ins != null)
					{
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}

	// line should consist of an MML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
	public Instruction getInstruction(String label) 
	{
		
		if(line.equals(""))
			{
				return null;
			} 
		
			//so we need to find the class of each instruction - get Lin to start		
			String instruction = scan();
			System.out.println("DEBUG - Initial Class Name: " + instruction);
			StringBuilder returnString = new StringBuilder(instruction.substring(0, 1).toUpperCase() + instruction.substring(1));
			returnString.append("Instruction");
			System.out.println("DEBUG - String BUilder Class Name: " + returnString);  //Correctly adds Lin to Instruction
			String className = returnString.toString(); //Class.forname needs a string not stringbuilder
			System.out.println("DEBUG - Returned Class Name: " + className);  //now capitalised
			
		
			
			
			String sml = "sml."; //required to look up via the package - doesnt work without
			//class.forname - requires try/catch
			try {
					String paramTempS;
					int paramTempI;
					Class<?> holdClass = Class.forName(sml + className); //error thrown on just class
					System.out.println("DEBUG - HoldClass: " + holdClass);
					Constructor<?> [] obj1 = holdClass.getConstructors();  //Instruction classes have more than one constructor
					Constructor<?> con = obj1[1]; //should be the constructor with arguments not default.
					
					Parameter [] param = con.getParameters(); //need to identify parameters that this constructor takes
					int paramSize = param.length;
					Object [] paramsToSend = new Object[paramSize]; //create an array of objects that we send to the Class, as they could be string or int
					
					//Iterate through the parameter (param[]) and obtain the correct amount and type in order to sent to the instruction
					for (int i = 0; i < paramSize; i++)
					{						
						System.out.println("DEBUG - Param:" + i + " " + param[i].getType());
						//set first as Label - as all 0 params = Label
						if (i == 0)
						{
							paramsToSend[0] = label;						
						}
						else if (i > 0)
						{
							//Extract the next object from the parameters types and then decide which one to return from scan/scanInt
							if(param[i].getType().equals("java.lang.String"))
							{
								paramTempS = scan();
								paramsToSend[i] = paramTempS;
								System.out.println("DEBUG - String identified");
							}
							else if (param[i].getType().equals(int.class))
							{
								paramTempI = scanInt();
								paramsToSend[i] = paramTempI;
								System.out.println("DEBUG - int identified");
							}
							else
							{
								System.out.println("DEBUG - No Types Found ");
							}
						}
					}
					
					Object obj2 = con.newInstance(paramsToSend);  //create a new instance of the Instruction with the correct parameters identified
					System.out.println("Debug - Instruction: " + con);
			
					//return the instruction - we could create new instance in the return but easier to read as is
				  return (Instruction)obj2;
				} 
			catch (ClassNotFoundException | SecurityException | InstantiationException |
					IllegalArgumentException | IllegalAccessException | InvocationTargetException e) 			
				{
				
				e.printStackTrace();
				}
			//Using Reflection -  remove calls to subclass, therefore remove the switch case
			
	
			
			//return new AddInstruction(label, r, s1, s2);   
	
	
			
			//return new SubInstruction(label, r, s1, s2);  
		
	 
			
			//return new MulInstruction(label, r, s1, s2);  
		
	   
			
			//retun new DivInstruction(label, r, s1, s2);  	
			
			
		
			//return new LinInstruction(label, r, s1);
			
		
	
			//return new OutInstruction(label, s1);  
			
	
			
			//return new BnzInstruction(label, s1, label2);  
			
	
	

		return null;
	}

	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
	private String scan() 
	{
		line = line.trim(); 
		if (line.length() == 0)
			return "";

		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t')  
		{
			i = i + 1;
		}
		String word = line.substring(0, i); 
		line = line.substring(i);  
		return word;
	}

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt() 
	{
		String word = scan(); 
		if (word.length() == 0) {
			return Integer.MAX_VALUE;
		}

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
}