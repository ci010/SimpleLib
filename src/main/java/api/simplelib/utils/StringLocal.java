package api.simplelib.utils;

/**
 * @author ci010
 */
public class StringLocal extends StringSource
{
	private String localized;

	public StringLocal(String id)
	{
		super(id);
		localized = Local.trans(id);
	}

	@Override
	public StringLocal setSource(Source source)
	{
		this.source = source;
		return this;
	}

	@Override
	public String toString()
	{
		if (source != null)
			return String.format(localized, source.getSource());
		else return localized;
	}
}
