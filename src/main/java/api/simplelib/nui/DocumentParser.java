package api.simplelib.nui;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author ci010
 */
public class DocumentParser
{
	public void parse(String xml) throws ParserConfigurationException, IOException, SAXException
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document parse = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
		org.w3c.dom.Document xmlDoc;
	}

}
