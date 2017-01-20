package api.simplelib.nui.std;

import api.simplelib.lang.Local;
import api.simplelib.nui.Element;

/**
 * @author ci010
 */
public class ElementText extends Element
{
	private String unlocalizedText;
	private String[] sourceID;

	public ElementText setUnlocalizedText(String unlocalizedText)
	{
		this.unlocalizedText = unlocalizedText;
		return this;
	}

	public ElementText setSourceID(String[] sourceID)
	{
		this.sourceID = sourceID;
		return this;
	}

	public String getUnlocalizedText() {return unlocalizedText;}

	public String[] getSourceID() {return sourceID;}

	private String getLocalizedText()
	{
		String trans = Local.trans(unlocalizedText);
		Object[] objects = new Object[sourceID.length];
		for (int i = 0; i < sourceID.length; i++)
			objects[i] = getContext().find(sourceID[i]);
		return String.format(trans, objects);
	}
}
