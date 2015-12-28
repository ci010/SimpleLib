package net.simplelib.gui;

/**
 * @author ci010
 */
public class SourceString
{
	private String rawContent;
	protected Source source;

	public SourceString(String rawContent)
	{
		this.rawContent = rawContent;
	}

	public SourceString setSource(Source source)
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
