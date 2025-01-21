package PS1;
///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:

    int[] register;
    int tap_index;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        register = new int[size];
        tap_index = tap;
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        if (seed.length != register.length) {
            throw new IllegalArgumentException("Invalid Seed Length");
        }
        for (int i = 0; i < register.length; i++ ) {
            if (seed[i] != 0 && seed[i] != 1) {
                throw new IllegalArgumentException("All seed values must be 0 or 1");
            };
        }
        for (int i = 0; i < register.length; i++ ) {
            register[i] = seed[i];
        }
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        int new_least = register[register.length-1] ^ register[tap_index];
        for (int i = register.length-1; i > 0; i-- ) {
            register[i] = register[i-1];
        }
        register[0] = new_least;
        //System.out.println(Arrays.toString(register));
        return new_least;
    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        int[] bits = new int[k];
        for (int i = 0; i < k; i++ ) {
            bits[i] = shift();
        }
        return toDecimal(bits);
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toDecimal(int[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++ ) {
            result *= 2;
            result += array[i];
        }
        return result;
    }
}
