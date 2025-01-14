import static org.junit.Assert.*;

import org.junit.Test;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {
    /**
     * Returns a shift register to test.
     * @param size
     * @param tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap) {
        return new ShiftRegister(size, tap);
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift2() {
        ILFShiftRegister r = getRegister(4, 2);
        int[] seed = { 1, 0, 0, 0 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 1, 1, 0, 1, 0, 1, 1, 1 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 6, 1, 7, 2, 2, 1, 6, 6, 2, 3 };
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    @Test
    public void testGenerate2() {
        ILFShiftRegister r = getRegister(4, 2);
        int[] seed = { 1, 0, 0, 0 };
        r.setSeed(seed);
        int[] expected = { 3, 5, 14, 2, 6, 11, 12, 4, 13, 7 };
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(4));
        }
    }

    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 1 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }

    /**
     * Tests register of length 6.
     */
    @Test
    public void testSixLength() {
        ILFShiftRegister r = getRegister(6, 3);
        int[] seed = { 1, 0, 1, 1, 1, 1 };
        r.setSeed(seed);
        int[] expected = { 0, 2, 1, 2, 3, 3, 1, 0, 2, 1 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(2));
        }
    }

    /**
     * Tests with erroneous seed.
     * Length greater than register size
     * It should throw an error
     */
    @Test
    public void testError1() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = { 1, 0, 0, 0, 1, 1, 0 };
        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            r.setSeed(seed);
        });
        
        assertEquals("Invalid Seed Length", exception.getMessage());
    }

    /**
     * Tests with erroneous seed.
     * Seed with non 0 and 1 values
     * It should throw an error
     */
    @Test
    public void testError2() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 5 };
        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            r.setSeed(seed);
        });
        
        assertEquals("All seed values must be 0 or 1", exception.getMessage());
    }
}
