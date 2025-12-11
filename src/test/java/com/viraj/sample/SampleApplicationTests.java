package com.viraj.sample;

// SPRING BOOT 3.x: JUnit 5 imports (instead of JUnit 4)
import org.junit.jupiter.api.Test;  // JUnit 5 Test annotation
import org.springframework.boot.test.context.SpringBootTest;
// NO MORE: @RunWith - JUnit 5 doesn't need it!

// SPRING BOOT 3.x: No @RunWith needed, SpringBootTest handles it
@SpringBootTest
public class SampleApplicationTests {

	@Test  // JUnit 5 @Test (from org.junit.jupiter.api.Test)
	public void contextLoads() {
	}

}
