package org.mapper.io.xml;

import org.mapper.knn.instance.ClassInstance;
import org.mapper.knn.Feature;
import org.mapper.knn.instance.FieldInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 */
public class XMLExporter {

    public static void export(String path, final List<ClassInstance> instances) {
        if (!path.contains("xml")) {
            path = path + ".xml";
        }
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();
            Element root = doc.createElement("patterns");
            doc.appendChild(root);

            for (final ClassInstance class_instance : instances) {
                final Element class_element = doc.createElement("class");

                class_element.setAttribute("name", class_instance.name);
                class_element.setAttribute("pattern", listToString(class_instance.features));

                root.appendChild(class_element);

                for (final FieldInstance field_instance : class_instance.fields) {
                    final Element field_element = doc.createElement("field");

                    field_element.setAttribute("name", field_instance.name);
                    field_element.setAttribute("pattern", listToString(field_instance.features));

                    class_element.appendChild(field_element);
                }
            }

            final TransformerFactory transformer_factory = TransformerFactory.newInstance();
            final Transformer transformer = transformer_factory.newTransformer();
            final DOMSource source = new DOMSource(doc);
            final StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/" + path));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static <T> String listToString(final List<Feature<T>> features) {
        final StringBuffer buffer = new StringBuffer();
        for (final Feature feature : features) {
            buffer.append(feature.attribute.key + "=" + feature.attribute.value + ",");
        }
        return buffer.toString();
    }

}
