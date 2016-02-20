package api.simplelib;

/**
 * @author ci010
 */
public class StringLocal extends StringSource
{
	private String localized;

	public StringLocal(String id)
	{
		super(id);
		localized = Local.translate(id);
	}

	@Override
	public String toString()
	{
		return String.format(localized, source.getSource());
	}
}
