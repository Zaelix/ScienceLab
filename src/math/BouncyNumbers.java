package math;

import java.math.BigInteger;
import java.util.HashMap;

public class BouncyNumbers {
	static HashMap<Integer,BigInteger> totals = new HashMap<Integer,BigInteger>();
	static String number;
	static boolean asc;
	static boolean desc;
	static char last;
	static String lastUnbouncy;
	public static void main(String[] args) {
		basicTest(100, 1000);
		basicTest(100, 10000);
		basicTest(100, 100000);
		basicTest(100, 1000000);
		basicTest(100, 10000000);
//		long normalStart = System.currentTimeMillis();
//		BigInteger tot=totalIncDecSym(9);
//		long normalEnd = System.currentTimeMillis();
//		System.out.println("Time taken: "+ (normalEnd - normalStart) + "ms");
		//efficiencyTest();
	}

	
	public static void efficiencyTest() {
		BigInteger extraLargeNum = BigInteger.valueOf(10).pow(1_000_000);
		
		
		for(int i = 0; i< 10; i++) {
			isBouncyEx(extraLargeNum);			
		}
		
		long newStart = System.currentTimeMillis();
		for(int i = 0; i< 10; i++) {
			isBouncyEx(extraLargeNum);			
		}
		long newEnd = System.currentTimeMillis();
		System.out.println("New: "+ (newEnd - newStart) + "ms");
		
		for(int i = 0; i< 10; i++) {
			isBouncy(extraLargeNum);			
		}

		long normalStart = System.currentTimeMillis();
		for(int i = 0; i< 10; i++) {
			isBouncy(extraLargeNum);			
		}
		long normalEnd = System.currentTimeMillis();
		System.out.println("Normal: "+ (normalEnd - normalStart) + "ms");
		
		
	}
	
	public static BigInteger basicTest(long start, long end) {
		BigInteger total = BigInteger.ZERO;
		for(BigInteger bi = BigInteger.valueOf(start); bi.compareTo(BigInteger.valueOf(end)) < 0; bi = bi.add(BigInteger.ONE)){
	    	if(!isBouncy(bi)) {
	    		total = total.add(BigInteger.ONE);
	    	}
	    }
		double percent = total.floatValue()/(end - start);
		System.out.println(String.format("%,07.4f", percent*100) +"% of numbers in range " + String.format("%,d", start) + " - " + String.format("%,d", end) + " are bouncy.");
		return total;
	}
	public static BigInteger totalIncDecEq(int x){
		if(x == 0) return BigInteger.valueOf(1);
		else if(x == 1) return BigInteger.valueOf(10);
		else if(x == 2) return BigInteger.valueOf(100);
		long total = (long) (0.831961*Math.pow(x, 5.37889) + 192.4);
	    System.out.println("Total for "+x+": "+total);
		return BigInteger.valueOf(total);
		
		// OR
		
		// total the asc and desc in each range using triangle numbers / binomial coefficient
	}
	public static BigInteger totalIncDecSym(int x){
		totals.put(0,BigInteger.valueOf(1));
	    //System.out.println("Called with "+x);
	    if(totals.containsKey(x)) return totals.get(x);
	    BigInteger total = BigInteger.valueOf(0);
	    int totIndex = 0;
	    for(int i = 0; i < x; i++) {
	    	if(totals.containsKey(i)) {
	    		total = totals.get(i);
	            //System.out.println("Found key for " + i);
	    		totIndex = i;
	    	}
	    	else totalIncDecSym(i);
	    }

	    BigInteger rangeTotal = BigInteger.valueOf(0);
	    BigInteger start = BigInteger.valueOf((long) Math.pow(10, totIndex));
	    BigInteger symEnd = BigInteger.valueOf((long) (Math.pow(10, x)*0.9));
	    BigInteger end = BigInteger.valueOf((long) (Math.pow(10, x)));
	    BigInteger midway = symEnd.subtract(start).divide(BigInteger.valueOf(2)).add(start);
	    for(BigInteger bi = start; bi.compareTo(midway) < 0; bi = bi.add(BigInteger.ONE)){
	    	if(!isBouncy(bi)) rangeTotal = rangeTotal.add(BigInteger.valueOf(2));
	    }
	    for(BigInteger bi = symEnd; bi.compareTo(end) < 0; bi = bi.add(BigInteger.ONE)){
	    	if(!isBouncy(bi)) rangeTotal = rangeTotal.add(BigInteger.valueOf(1));
	    }
	    total = total.add(rangeTotal);
	    //System.out.println("Range Total for "+x+": "+rangeTotal);
	    System.out.println("Total for "+x+": "+total);
		totals.put(x,total);
	    return total;
	}
	public static BigInteger totalIncDec(int x){
		totals.put(0,BigInteger.valueOf(1));
	    //System.out.println("Called with "+x);
	    if(totals.containsKey(x)) return totals.get(x);
	    BigInteger total = BigInteger.valueOf(0);
	    int totIndex = 0;
	    for(int i = 0; i < x; i++) {
	    	if(totals.containsKey(i)) {
	    		total = totals.get(i);
	            //System.out.println("Found key for " + i);
	    		totIndex = i;
	    	}
	    	else totalIncDec(i);
	    }
	    BigInteger start = BigInteger.valueOf((long) Math.pow(10, totIndex));
	    BigInteger end = BigInteger.valueOf((long) Math.pow(10, x));
	    for(BigInteger bi = start; bi.compareTo(end) < 0; bi = bi.add(BigInteger.ONE)){
	    	if(!isBouncy(bi)) total = total.add(BigInteger.ONE);
	    }
	    System.out.println("Total for "+x+": "+total);
		totals.put(x,total);
	    return total;
	  }
	  
	  public static boolean isBouncy(BigInteger num){
		  //System.out.print("Checking "+num.toString() + "...");
	    if(num.compareTo(BigInteger.valueOf(100)) < 0) {
	    	//System.out.print("Not bouncy. Too low.\n");
	    	return false;
	    }
	    String number = num.toString();
	    boolean asc = true;
	    boolean desc = true;
	    char last = number.charAt(0);
	    for(int i = 1; i < number.length(); i++) {
	    	char c = number.charAt(i);
	    	if(asc && c < last) {
	    		asc = false;
	    		//System.out.print("Not ascending. ");
	    	}
	    	else if(desc && c > last) {
	    		desc = false;
	    		//System.out.print("Not descending. ");
	    	}
	    	if(!(asc || desc)) break;
	    	last = c;
	    }
	    boolean result = !(asc || desc);
	    //if(result) System.out.print("Bouncy!\n");
	    //else System.out.print("Not bouncy.\n");
	    return result;
	  }
	  
	  public static boolean isBouncyEx(BigInteger num){
		  String number = num.toString();
		    boolean asc = true;
		    boolean desc = true;
		    char last = number.charAt(0);
		    for(int i = 1; i < number.length(); i++) {
		    	char c = number.charAt(i);
		    	if(c == last) continue;
		    	else if(asc && c < last) {
		    		asc = false;
		    	}
		    	else if(desc && c > last) {
		    		desc = false;
		    	}
		    	if(!(asc || desc)) break;
		    	last = c;
		    }
		    boolean result = !(asc || desc);
		    return result;
	  }
}
