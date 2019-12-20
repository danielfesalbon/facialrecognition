/**
 * 
 */
package com.cv.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cv.main.Defaults;

/**
 * @author Daniel Fesalbon
 *
 */
public class FileTools {

	private static Logger logger = Log.getLogger("FileTools");

	public static String getName(String imgname) {
		try {
			File xml = new File(Defaults.FACES);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);

			// optional, but recommended. read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("person");
			for (int i = 0; i < nList.getLength(); i++) {
				Node n = nList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					if (e.getElementsByTagName("image").item(0).getTextContent().equals(imgname)) {
						return e.getElementsByTagName("name").item(0).getTextContent();
					}
				}
			}
		} catch (ParserConfigurationException e) {
			logger.warning(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			logger.warning(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			logger.warning(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void addElement(String name, String imgfile) {
		try {
			File xml = new File(Defaults.FACES);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);

			// optional, but recommended. read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("person");

			Element person = doc.createElement("person");
			Element imgname = doc.createElement("name");
			imgname.appendChild(doc.createTextNode(name));
			Element image = doc.createElement("image");
			image.appendChild(doc.createTextNode(imgfile));
			person.appendChild(imgname);
			person.appendChild(image);
			nodes.item(0).getParentNode().insertBefore(person, nodes.item(0));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(Defaults.FACES));

			transformer.transform(source, result);

//			String xmlOutput = result.getWriter().toString();
//			System.out.println(xmlOutput);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static Integer getFileName() {
		try {
			FilenameFilter imgFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					name = name.toLowerCase();
					return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
				}
			};

			File[] imageFiles = new File(Defaults.IMAGES).listFiles(imgFilter);
			return imageFiles.length + 1;
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

}
