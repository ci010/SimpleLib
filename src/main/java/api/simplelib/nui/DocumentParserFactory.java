package api.simplelib.nui;

/**
 * @author ci010
 */
public class DocumentParserFactory
{
	private static DocumentParserFactory INSTANCE = new DocumentParserFactory();

	public static DocumentParserFactory instance() {return INSTANCE;}

	public DocumentParserFactory register(Class<? extends Element> elementClass)
	{
		return this;
	}

	private DocumentParserFactory() {}

	public interface ElementParser
	{
		Element parse(org.w3c.dom.Element element);
	}

	public interface Render<T extends Element>
	{
		void render(T element);
	}
}
