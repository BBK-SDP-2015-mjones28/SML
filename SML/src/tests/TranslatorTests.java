package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import sml.AddInstruction;
import sml.Instruction;
import sml.Labels;
import sml.LinInstruction;
import sml.Machine;
import sml.MulInstruction;
import sml.Registers;
import sml.Translator;


/**
 * Tests of the read and Translate - and getInstruction
 * Both scanInt and Scan are private and as such makes it difficult to test without changing them to public
 * Or adding boilerplate code. (Told not to change or add code outside of getInstruction).
 * The translator class does not use @Data lombok - or not present.
 * Therefore they will not be tested
 * @author mikieJ
 *
 */


public class TranslatorTests 
{

	Machine m = new Machine();	
	String[] args = {"smlIns.txt"};
	Translator t;

	
	@Before
	public void setUpBeforeClass() throws Exception 
	{
		t = new Translator(args[0]); 
	}


	
	/**
	 * tests whether the instructions are loaded in correctly
	 * readAndTranslate always returns false so no need to test the actual return
	 */
	@Test
	public void readTranslateTest() 
	{				
		Translator t = new Translator(args[0]);  		
		assertFalse(t.readAndTranslate(m.getLabels(), m.getProg()));  //always returns false	
		
		//first line of the provided instructions
		Instruction ins = new LinInstruction("f0", 20, 6);		
		assertEquals(m.getProg().get(0).toString(), ins.toString());
		//second line of the provided instructions
		Instruction ins1 = new LinInstruction("f1", 21, 1);			
		assertEquals(m.getProg().get(1).toString(), ins1.toString());
		//fourth line of the provided instructions
		Instruction ins2 = new MulInstruction("f3", 21, 21, 20);		
		assertEquals(m.getProg().get(3).toString(), ins2.toString());
	}
	
	
	//had to make line public - cant figure out another way to test this otherwise
	@Test
	public void getInstructionTest() 
	{
		Translator t = new Translator(args[0]);  		
		t.readAndTranslate(m.getLabels(), m.getProg()); 
		
		//*** LINE HAS BEEN MADE PUBLIC IN TRANSLATOR CLASS *****//
		t.line = "lin 10 1";
				
		Instruction ins = t.getInstruction("f0");
		Instruction ins1 = new LinInstruction("f0", 10, 1);	
		assertEquals(ins.toString(), ins1.toString());	
		
	}

	
	
	@Ignore
	public void getInstructionfinalTest() 
	{		
		System.out.println("Main Test Class running");				
		
		Machine m = new Machine();
		Translator t = new Translator(args[0]);  
		t.readAndTranslate(m.getLabels(), m.getProg()); 

		m.execute();
		//from non-reflection tests - know the answer is 21 = 720, 22 = 1
		assertEquals(m.getRegisters().getRegister(21), 720);
		assertEquals(m.getRegisters().getRegister(22), 1);
	}
	

}
