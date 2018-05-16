package com.guerzonica.app.models.amazon;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.guerzonica.app.models.data.ProductDetails;

import org.w3c.dom.Document;

public class AmazonResponse {

    public static ProductDetails parse(String xmlString) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream     input   = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        
        Document doc  = builder.parse(input);
            doc.getDocumentElement().normalize();

        String link = doc.getElementsByTagName("DetailPageURL").item(0).getTextContent()
                            .split("\\?")[0];

        String clonedTree =
            "<product>" +
                "<asin>" +
                    doc.getElementsByTagName("ASIN").item(0).getTextContent() +
                "</asin>" +
                "<name>" +
                    doc.getElementsByTagName("Title").item(0).getTextContent() +
                "</name>" +
                "<image>" +
                    doc.getElementsByTagName("MediumImage").item(0)
                        .getChildNodes().item(0).getTextContent() +
                "</image>" +
                "<description>" +
                    "Powered by amazon" +
                "</description>" +
                "<price>" +
                    doc.getElementsByTagName("LowestNewPrice").item(0)
                        .getChildNodes().item(0).getTextContent() +
                "</price>" +
                "<link>" +
                    link +
                "</link>" +
            "</product>";

        InputStream output = new ByteArrayInputStream(clonedTree.getBytes(StandardCharsets.UTF_8));

        JAXBContext    jaxbC = JAXBContext.newInstance(ProductDetails.class);
        Unmarshaller   jaxbU = jaxbC.createUnmarshaller();
        ProductDetails item  = (ProductDetails) jaxbU.unmarshal(builder.parse(output));

        return item;
    }

}