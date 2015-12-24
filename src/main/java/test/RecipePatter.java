package test;

/**
 * @author ci010
 */
public class RecipePatter
{
	String name;
	String domain;
	int id, length, height;
	boolean shapeless;
	String[][] recipe = new String[3][3];

	public RecipePatter(String domain, String name, int id, boolean shapeless)
	{
		this.domain = domain;
		this.name = name;
		this.id = id;
		this.shapeless = shapeless;
	}
}
