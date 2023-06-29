package Etc;

public class TestMaker {
	public static String testOutput = "psaysnhfrrqgxwik:rkacypviuburk:13\r\n" + "pdyjrkaylryr:rkacypviuburk:8\r\n"
			+ "lnjhrzfrosinb:rkacypviuburk:10\r\n" + "afirbipbmkamjzw:rkacypviuburk:12\r\n"
			+ "loogviwcojxgvi:rkacypviuburk:11\r\n" + "iqkyztorburjgiudi:rkacypviuburk:13\r\n"
			+ "cwhyyzaorpvtnlfr:rkacypviuburk:12\r\n" + "iroezmixmberfr:rkacypviuburk:10\r\n"
			+ "jhjyasikwyufr:rkacypviuburk:11\r\n" + "tklquxrnhfiggb:rkacypviuburk:11\r\n"
			+ "cpnqknjyviusknmte:rkacypviuburk:10\r\n" + "hwzsemiqxjwfk:rkacypviuburk:11\r\n"
			+ "ntwmwwmicnjvhtt:rkacypviuburk:13\r\n" + "emvquxrvvlvwvsi:rkacypviuburk:12\r\n"
			+ "sefsknopiffajor:rkacypviuburk:13\r\n" + "znystgvifufptxr:rkacypviuburk:13\r\n"
			+ "xuwahveztwoor:rkacypviuburk:11\r\n" + "hrwuhmtxxvmygb:rkacypviuburk:11\r\n"
			+ "karpscdigdvucfr:rkacypviuburk:9\r\n" + "xrgdgqfrldwk:rkacypviuburk:10\r\n"
			+ "nnsoamjkrzgldi:rkacypviuburk:12\r\n" + "ljxzjjorwgb:rkacypviuburk:11\r\n"
			+ "cfvruditwcxr:rkacypviuburk:10\r\n" + "eglanhfredaykxr:rkacypviuburk:12\r\n"
			+ "fxjskybblljqr:rkacypviuburk:11\r\n" + "qifwqgdsijibor:rkacypviuburk:12\r\n"
			+ "xikoctmrhpvi:rkacypviuburk:9\r\n" + "ajacizfrgxfumzpvi:rkacypviuburk:13\r\n"
			+ "mhmkakybpczjbb:rkacypviuburk:10\r\n" + "vkholxrvjwisrk:rkacypviuburk:11\r\n"
			+ "npyrgrpbdfqhhncdi:rkacypviuburk:14\r\n" + "pxyousorusjxxbt:rkacypviuburk:12\r\n"
			+ "jcocndjkyb:rkacypviuburk:10\r\n" + "fxpvfhfrujjaifr:rkacypviuburk:11\r\n"
			+ "hkldhadcxrjbmkmcdi:rkacypviuburk:14\r\n" + "hirldidcuzbyb:rkacypviuburk:9\r\n"
			+ "ggcvrtxrtnafw:rkacypviuburk:11\r\n" + "tdvibqccxr:rkacypviuburk:11\r\n"
			+ "osbednerciaai:rkacypviuburk:10\r\n" + "qojfrlhufr:rkacypviuburk:9\r\n"
			+ "kqijoorfkejdcxr:rkacypviuburk:11\r\n" + "zqdrhpviqslik:rkacypviuburk:10\r\n"
			+ "clxmqmiycvidiyr:rkacypviuburk:13\r\n" + "xffrkbdyjveb:rkacypviuburk:8\r\n"
			+ "dyhxgviphoptak:rkacypviuburk:12\r\n" + "dihhiczkdwiofpr:rkacypviuburk:13\r\n"
			+ "riyhpvimgaliuxr:rkacypviuburk:9\r\n" + "fgtrjakzlnaebxr:rkacypviuburk:10\r\n"
			+ "ppctybxgtleipb:rkacypviuburk:10\r\n" + "ucxmdeudiycokfnb:rkacypviuburk:14";

	public static void main(String[] args) {
		String[] lines = testOutput.split("\n");
		String[] words = new String[lines.length];
		String finalOutput = "Dictionary dictionary = new Dictionary(new String[]{";
		for (int i = 0; i < lines.length; i++) {
			words[i] = lines[i].split(":")[0];
			finalOutput += "\"" + words[i] + "\",";
		}
		finalOutput += "});";
		System.out.println(finalOutput);
	}
}
