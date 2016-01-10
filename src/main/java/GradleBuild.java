import java.io.*;

/**
 * @author ci010
 */
public class GradleBuild
{
	static String BUILD = "build", BUILD_DEV = "dev";

	public static void main(String[] args)
	{
		gradle(BUILD);
	}

	static void gradle(String cmd)
	{
		System.out.println("gradlew ".concat(cmd));
		try
		{
			File file = new File("").getCanonicalFile();
			Process process = Runtime.getRuntime().exec(file.toString().concat("\\gradlew.bat ").concat(cmd));
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
			String line;
			while ((line = input.readLine()) != null)
				System.out.println(line);
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}
