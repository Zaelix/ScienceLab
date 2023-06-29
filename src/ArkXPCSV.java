
public class ArkXPCSV {
	public static void main(String[] args) {
		//dinoXPLenient();
		playerXPLenient();
	}
	
	public static void playerXPEngrams() {
		for(int i = 0; i < 500; i++) {
			long xp =  (long) (1.3846*Math.pow(i, 3.4022)+1);
			int engrams = 8+ 4*(i/8);
			System.out.println(i + ";"+xp+";"+engrams);
		}
	}
	public static void playerXPLenient() {
		for(int i = 0; i < 700; i++) {
			long xp =  (long) (5224522000000L + (59683.97 - 5224522000000L)/(1 + Math.pow(i/348509.6,1.945849)));
			int engrams = 8+ 4*(i/8);
			System.out.println(i + ";"+xp+";"+engrams);
		}
	}
	
	public static void dinoXPPower() {
		for(int i = 0; i < 500; i++) {
			long xp =  (long) (1.59662*Math.pow(i, 3.67153)+1.00646);
			System.out.println(i + ";"+xp);
		}
	}
	public static void dinoXPExponential() {
		for(int i = 1; i < 117; i++) {
			long xp =  (long) (-5.781029 + 13.41255*Math.pow(Math.E,(+0.1626178*i)));
			System.out.println(i + ";"+xp);
		}
	}
	public static void dinoXPLenient() {
		for(int i = 1; i < 500; i++) {
			long xp =  (long) (4884071000000L + (-515.8776 - 4884071000000L)/(1 + Math.pow(i/109394.7, 2.22078)));
			System.out.println(i + ";"+xp);
		}
	}
}
