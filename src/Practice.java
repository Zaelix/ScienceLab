import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Random;

public class Practice {
	static String word;
	static HashMap<Long, BigInteger[]> values = new HashMap<Long, BigInteger[]>();
	static void makeWord() {
		word = "twosday ";
		
		for(int i = 0; i<7; i++) {
			word += makeLetter();
		}
		
	}
	
	static char makeLetter() {
		Random gen = new Random();
		int num = gen.nextInt(26) + 97;
		char letter = (char)num;
		return letter;
	}

	public static void main(String[] args) {
		//makeWord();
		//System.out.println(word);
		
		BigInteger b1 = pad(1000000);

		long start2 = System.currentTimeMillis();
		BigInteger b2 = pad(1000000);
		long end2 = System.currentTimeMillis();
		System.out.println("Pad Took " + (end2 - start2) + "ms");
	}
	static BigInteger calc(long n){
	    BigDecimal p = new BigDecimal(1.324717957244746025960908854);
	    BigDecimal s = new BigDecimal(1.0453567932525329623);
	    MathContext mc = new MathContext(15);
	    long start = System.currentTimeMillis();
	    BigDecimal result = p.pow((int)(n-1));
	    long end = System.currentTimeMillis();
	    System.out.println("Power took " + (end - start) + "ms");
	    result = result.divide(s, mc);
	    result = result.add(new BigDecimal(0.5));
	    return result.toBigInteger();
	}
	
	
	static BigInteger pad(long n)
    {
		
        BigInteger pPrevPrev = BigInteger.ONE, pPrev = BigInteger.ONE,
            pCurr = BigInteger.ONE, pNext = BigInteger.ONE;
        for(long i = n; i > 0; i--) {
        	if(values.containsKey(n)) {
    			pPrevPrev = values.get(n)[0];
    			pPrev = values.get(n)[1];
    			pCurr = values.get(n)[2];
    			pNext = values.get(n)[3];
    			break;
    		}
        }
        
        for (long i = 3; i <= n; i++) {
        		pNext = pPrevPrev.add(pPrev);
	            pPrevPrev = pPrev;
	            pPrev = pCurr;
	            pCurr = pNext;
        }
        values.put(n, new BigInteger[] {pPrevPrev, pPrev, pCurr, pNext});
        return pNext;
    } 
}