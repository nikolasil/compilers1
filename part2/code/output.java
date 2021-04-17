import java.lang.Math;
public class output {
	public static String findLangType(String langName) {
		if("Java".startsWith(langName))
			if(langName.startsWith("Java"))
				return "Static";
		else
			if("script".endsWith(langName))
				return "Dynamic"+"kostas";
		else
				return "Unknown"+"kostas"+"kostas"+"kostas";
		else
			if("script".endsWith(langName))
				return "Probably Dynamic";
		else
				return "Unknown";
	}

	public static void main(String[] args) {
		System.out.println(findLangType("Java"));
		System.out.println(findLangType("Javascript"));
		System.out.println(findLangType("Typescript"));
	}
}