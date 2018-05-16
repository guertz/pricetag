package com.guerzonica.app.models.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@XmlRootElement
class Customer {

    String name;
    int age;
    int id;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

}

public class Test {
    public static void run() {
        try {
        String xmlString = 
            "<xyz>" +
                "<request>" +
                    "<details></details>" +
                "</request>" +
                "<customer id=\"100\">" +
                    "<age>29</age>" +
                    "<name>mkyong</name>" +
                "</customer>" +
            "</xyz>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream     input    = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        Document        document = builder.parse(input);
         
        document.getDocumentElement().normalize();
         
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());
         
        //Get all customers
        NodeList nList = document.getElementsByTagName("customer");

        JAXBContext  jaxbC = JAXBContext.newInstance(Customer.class);
        Unmarshaller jaxbU = jaxbC.createUnmarshaller();
        Customer     item  = (Customer) jaxbU.unmarshal(nList.item(0));

        System.out.println(item.age);

        } catch(Exception e) { e.printStackTrace(); }
    }
}