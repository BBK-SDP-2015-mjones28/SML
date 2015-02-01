package tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import sml.AddInstruction;
import sml.Instruction;
import sml.LinInstruction;
import sml.Machine;
import sml.Translator;


/**
 * 
 * @author mikieJ
 * Testing the New getInstruction method ready for reflection conversion
 * Broke each part of the necessary reflection elements up, then constructed them in separate methods
 * Upon completion i tested the whole method as one one it was copied into the translator class - 
 * the getInstructionfinalTest confirms this. 
 */

public class reflectionTransTest 
{
	static Machine m;
	static Translator t;
	static String className;	
	static Class<?> holdClass;
	ArrayList<Instruction> program; 
	
	@Before
	public void setupBefore()
	{
		System.out.println("Before class is invoked");
		m = new Machine();
		program = new ArrayList<Instruction>();
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		System.out.println("After class is invoked");
	}	

	
	/**
	 * Tests the extraction of the Instruction word, capitalised the first letter
	 * then concatenates it with sml and Instruction
	 * E.g. "sml." + "Add" + "Instruction" - sml.AddInstruction
	 */
	@Test
	public void classStringBuilder()
	{
		String instruction = scan("add"); //scan already tested and correctly identifies the math operation
		StringBuilder returnString = new StringBuilder("sml." + instruction.substring(0, 1).toUpperCase() + instruction.substring(1));
		returnString.append("Instruction");	
		className = returnString.toString();	
		assertEquals(className,"sml.AddInstruction");
	}
	
	
	/**
	 * Used to replicate the Translator.scan() method
	 * This has already been created by Keith and thus I am not  testing it
	 * @param sign (String: mathematical instruction"
	 * @return passed String
	 */
	public String scan(String sign)
	{
		return sign;
	}

	/**
	 * Simple get the class of the provided String
	 * In this case the test sample is AddInstruction "sml.AddInstruction"
	 * @throws ClassNotFoundException
	 */
	@Test
	public void getClassTest() throws ClassNotFoundException
	{			
		//ClassName is an AddInstruction.class - Instruction type already extracted - classStringBuilder
		holdClass = Class.forName(className); 
		assertEquals(holdClass, AddInstruction.class);
	}


	/**
	 * Gets the Class's constructor
	 * In this instance the AddInstruction.class is used
	 * @throws ClassNotFoundException
	 *
	 */
	@Test
	public void getClassConstructorTest() throws ClassNotFoundException
	{	
		//ClassName is an AddInstruction.class.  Instruction type already extracted - classStringBuilder
		Constructor<?> [] obj  = holdClass.getConstructors();	
		assertNotNull(obj[1]);
	}
	
	/**
	 * Gets the Class's constructor array length
	 * In this instance the AddInstruction.class is used
	 * @throws ClassNotFoundException
	 */
	@Test
	public void getClassConstructorLengthTest() throws ClassNotFoundException
	{		
		//ClassName is an AddInstruction.class.  Instruction type already extracted - classStringBuilder
		Constructor<?> [] obj  = holdClass.getConstructors();	
		int objLength = obj.length;
		assertFalse(objLength == 1);
	}
	
	/**
	 * Gets the parameters from the AddInstruction.class
	 * AddInstruction used as the test string instruction
	 */
	@Test
	public void getParamsTest()
	{		
		//ClassName is an AddInstruction.class.  Instruction type already extracted - classStringBuilder
		Constructor<?> [] obj  = holdClass.getConstructors();	
		Constructor<?> con = obj[1];
		Parameter [] param = con.getParameters(); 
		int paramSize = param.length;
		assertEquals(param[0].getType(), java.lang.String.class);
		assertEquals(param[1].getType(), int.class);
		assertEquals(param[2].getType(), int.class);
		assertEquals(param[3].getType(), int.class);
	}
	
	
	@Test
	public void setInitialParamTest() throws ClassNotFoundException 
	{
		//label is passed and already extracted from keiths code - no need to test
		String label = "f0";
		//holdClass Required here - otherwise wont compile
		//ClassName is an AddInstruction.class.  Instruction type already extracted - classStringBuilder
		holdClass = Class.forName(className); 
		
		Constructor<?> [] obj  = holdClass.getConstructors();	
		Constructor<?> con = obj[1];
		Parameter [] param = con.getParameters();
		int paramSize = param.length;
		Object [] paramsToSend = new Object[paramSize]; 
		paramsToSend[0] = label;
		assertEquals(paramsToSend[0].getClass(),java.lang.String.class);
		assertEquals(paramsToSend[0],"f0");
	}
	
	
	
	/**
	 * Sets the parameters ready to send an an instruction back to readAndTranslate
	 */
	@Test
	public void setRemainingParamsTest()
	{
		//holdClass Required here - otherwise wont compile
		//ClassName is an AddInstruction.class .  Instruction type already extracted - classStringBuilder
		
		//the instruction has already been removed at this point  - tested successfully above
		String[] instruct = {"f0", "0", "10", "20"};
		Object[] expResult = {"f0", 0, 10, 20};
		Object[] nonExpResults = {"f0", 1, 10, 20};
		
		Constructor<?> [] obj  = holdClass.getConstructors();	
		Constructor<?> con = obj[1];
		Parameter [] param = con.getParameters();
		int paramSize = param.length;
	
		Object [] paramsToSend = new Object[paramSize]; 
		paramsToSend[0] = instruct[0];
		for (int i = 1; i < paramSize; i++)
		{							
				//Extract the next object from the parameters types 
				//decide which object type to return from scan/scanInt
				if(param[i].getType().equals(java.lang.String.class))
				{
					paramsToSend[i] = instruct[i]; //replaces Keith's scan method for this test - know it returns 
									  //scan()			//correct item - this test whether i call the correct method
				}
				else if (param[i].getType().equals(int.class))
				{
					paramsToSend[i] = Integer.parseInt(instruct[i]); //replaces Keith's scanInt method for this test - know it returns 
										//scanInt()								//correct item - this test whether i call the correct method
				}						
		}
		assertEquals(paramsToSend[0], expResult[0]);		
		assertEquals(paramsToSend[1], expResult[1]);	
	}
	
	/**
	 * Tests to see if an Instruction is correctly added to program array
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void sendingParamsTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		Class correctClass = sml.AddInstruction.class;
		Class incorrectClass = sml.LinInstruction.class;
		
		//holdClass Required here - otherwise wont compile
		//ClassName is an AddInstruction.class
		holdClass = Class.forName(className); 
		Constructor<?> [] obj  = holdClass.getConstructors();	
		Constructor<?> con = obj[1];

		Object[] params = {"f0", 0, 10, 20};
			
		program.add((Instruction)con.newInstance(params));
		//only one instruction added to this list - this get(0)
		assertEquals(program.get(0).getClass(),correctClass);		
	}
	

	/**
	 * Final test of reflection in translator.class using the set of Instructions provided 
	 * Completed using the actual classes now added to the sml package 
	 * and the full reflection method added to translator.getInstruction()
	 * The above has been correctly copied into tranlator.class
	 */
	@Test
	public void getInstructionfinalTest() 
	{		
		System.out.println("Main Test Class running");				
		String[] args = {"smlIns.txt"};
		
		Machine m = new Machine();
		Translator t = new Translator(args[0]);  
		t.readAndTranslate(m.getLabels(), m.getProg()); 

		m.execute();
		//from non-reflection tests - know the answer is 21 = 720, 22 = 1
		assertEquals(m.getRegisters().getRegister(21), 720);
		assertEquals(m.getRegisters().getRegister(22), 1);
	}

}
