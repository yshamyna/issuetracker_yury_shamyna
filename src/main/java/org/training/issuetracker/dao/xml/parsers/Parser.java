package org.training.issuetracker.dao.xml.parsers;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class Parser {
	private static URL url;
	
	public static void parse(DefaultHandler parser) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance(); 
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(new InputSource(url.openStream()), parser);
	}
	
	public static void setURL(URL url) {
		Parser.url = url;
	}
}
