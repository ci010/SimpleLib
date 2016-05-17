package api.simplelib.utils;

/**
 * @author ci010
 */
public class StringSource implements CharSequence
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

	public Source source()
	{
		return source;
	}

	public interface Source
	{
		Object[] getSource();
	}

	@Override
	public int length()
	{
		return rawContent.length();
	}

	@Override
	public char charAt(int index)
	{
		return rawContent.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end)
	{
		return rawContent.subSequence(start, end);
	}

	public String getRawContent()
	{
		return rawContent;
	}

	@Override
	public String toString()
	{
		if (source != null)
			return String.format(rawContent, source.getSource());
		else return rawContent;
	}
}
