package Etc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClosestWordTest {

	@Test
	void test() {
		ClosestWord dictionary = new ClosestWord(new String[]{"psaysnhfrrqgxwik","pdyjrkaylryr","lnjhrzfrosinb","afirbipbmkamjzw",
                "loogviwcojxgvi","iqkyztorburjgiudi","cwhyyzaorpvtnlfr","iroezmixmberfr","jhjyasikwyufr","tklquxrnhfiggb",
                "cpnqknjyviusknmte","hwzsemiqxjwfk","ntwmwwmicnjvhtt","emvquxrvvlvwvsi","sefsknopiffajor","znystgvifufptxr",
                "xuwahveztwoor","hrwuhmtxxvmygb","karpscdigdvucfr","xrgdgqfrldwk","nnsoamjkrzgldi","ljxzjjorwgb",
                "cfvruditwcxr","eglanhfredaykxr","fxjskybblljqr","qifwqgdsijibor","xikoctmrhpvi","ajacizfrgxfumzpvi",
                "mhmkakybpczjbb","vkholxrvjwisrk","npyrgrpbdfqhhncdi","pxyousorusjxxbt","jcocndjkyb","fxpvfhfrujjaifr",
                "hkldhadcxrjbmkmcdi","hirldidcuzbyb","ggcvrtxrtnafw","tdvibqccxr","osbednerciaai","qojfrlhufr",
                "kqijoorfkejdcxr","zqdrhpviqslik","clxmqmiycvidiyr","xffrkbdyjveb","dyhxgviphoptak","dihhiczkdwiofpr",
                "riyhpvimgaliuxr","fgtrjakzlnaebxr","ppctybxgtleipb","ucxmdeudiycokfnb",});
    assertEquals("psaysnhfrrqgxwik",dictionary.findMostSimilar("psaysnhqgxwik"));
    assertEquals("cwhyyzaorpvtnlfr",dictionary.findMostSimilar("cwhyyzffrpftnlfr"));
    assertEquals("ntwmwwmicnjvhtt",dictionary.findMostSimilar("ntzzwmwwmicznjvhtt"));
    assertEquals("karpscdigdvucfr",dictionary.findMostSimilar("karpsdizdvucffr"));
    assertEquals("fxjskybblljqr",dictionary.findMostSimilar("fxjsfybbbllljqr"));
    assertEquals("zqdrhpviqslik",dictionary.findMostSimilar("rkacypviuburk"));
	}

}
