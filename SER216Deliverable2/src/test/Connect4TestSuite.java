package test;

import org.junit.runners.Suite.SuiteClasses;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@SuiteClasses({Connect4Test.class,
	Connect4ComputerPlayerTest.class,
	Connect4ServerTest.class})

public class Connect4TestSuite {

}