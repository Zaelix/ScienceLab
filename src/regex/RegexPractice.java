package regex;

public class RegexPractice {
	public static void main(String[] args) {
		String fixedEmail = replaceEmailDomain("Banana@.com");
		System.out.println(fixedEmail);
	}
	
	static String replaceEmailDomain(String email) {
		String mail = email.replaceAll("(?<=@)[\\w]{0,}(?=.com)", "mail");
		//String mail = email.replaceAll("(?<=@)(.*)(?=[.][\\w]{2,})", "mail");
		return mail;
	}
}
