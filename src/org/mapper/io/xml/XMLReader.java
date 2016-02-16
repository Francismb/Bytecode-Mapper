package org.mapper.io.xml;

import org.mapper.knn.instance.ClassInstance;
import org.mapper.knn.instance.FieldInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class XMLReader {

    private static final Pattern regex = Pattern.compile("([A-Z0-9]+)=([0-9.]+),");

    public static List<ClassInstance> read(String path) {
        if (!path.contains(".xml")) {
            path = path + ".xml";
        }
        Matcher matcher;
        final List<ClassInstance> instances = new ArrayList<>();
        try {
            final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(System.getProperty("user.dir") + "/" + path);
            final NodeList classes = doc.getElementsByTagName("class");
            for (int i = 0; i < classes.getLength(); i++) {
                final Node node = classes.item(i);
                if (node instanceof Element) {
                    final Element class_element = (Element) node;
                    final ClassInstance instance = new ClassInstance();
                    {
                        instance.name = class_element.getAttribute("name");
                        final String pattern = class_element.getAttribute("pattern");
                        matcher = regex.matcher(pattern);
                        while (matcher.find()) {
                            instance.attributes.put(matcher.group(1), Double.valueOf(matcher.group(2).replace(",", "")));
                        }
                    }
                    {
                        final NodeList field_elements = class_element.getElementsByTagName("field");
                        if (field_elements != null && field_elements.getLength() > 0) {
                            for (int e = 0; e < field_elements.getLength(); e++) {
                                final Element field_element = (Element) field_elements.item(e);
                                final FieldInstance field_instance = new FieldInstance();
                                field_instance.name = field_element.getAttribute("name");
                                final String pattern = field_element.getAttribute("pattern");
                                matcher = regex.matcher(pattern);
                                while (matcher.find()) {
                                    field_instance.attributes.put(matcher.group(1), Double.valueOf(matcher.group(2).replace(",", "")));
                                }
                                instance.fields.add(field_instance);
                            }
                        }
                    }
                    instances.add(instance);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return instances;
    }
}
