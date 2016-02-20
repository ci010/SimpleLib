package api.simplelib;

/**
 * @author ci010
 */
public class StringSource
{
	private String rawContent;
	protected Source source;

	public StringSource(String rawContent)
	{
		this.rawContent = rawContent;
	}

	public StringSource setSource(Source source)
	{
		this.source = source;
		return this;
	}

	public interface Source
	{
		Object[] getSource();
	}

	@Override
	public String toString()
	{
		return String.format(rawContent, source.getSource());
	}
}
