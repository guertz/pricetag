package com.guerzonica.app.http.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.guerzonica.app.storage.models.Offer;

import org.w3c.dom.Document;

public class AmazonResponse {

    public static Offer parse(String xmlString) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream     input   = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        
        Document doc  = builder.parse(input);
            doc.getDocumentElement().normalize();

        String link = doc.getElementsByTagName("DetailPageURL").item(0).getTextContent()
                            .split("\\?")[0];

        String clonedTree =
            "<offer>" +
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
                    "<link>" +
                        link +
                    "</link>" +
                "</product>" + 
                "<value>" +
                    doc.getElementsByTagName("LowestNewPrice").item(0)
                        .getChildNodes().item(0).getTextContent() +
                "</value>" +
            "</offer>";

        InputStream output = new ByteArrayInputStream(clonedTree.getBytes(StandardCharsets.UTF_8));

        JAXBContext    jaxbC = JAXBContext.newInstance(Offer.class);
        Unmarshaller   jaxbU = jaxbC.createUnmarshaller();
        Offer offer  = (Offer) jaxbU.unmarshal(builder.parse(output));

        return offer;
    }

}