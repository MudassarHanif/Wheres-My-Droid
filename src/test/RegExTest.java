package test;

public class RegExTest {

	public static void mian(String []args){
		String line = "Intent { act=android.intent.action.DELETE dat=package:com.ts.activities cmp=com.android.packageinstaller/.UninstallerActivity }";
		System.out.println(line.matches(".*UninstallerActivity.*"));
		
	}
}
