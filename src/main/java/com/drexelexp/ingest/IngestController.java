package com.drexelexp.ingest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.drexelexp.HomeController;

@Controller
public class IngestController {
	private static final Logger logger = LoggerFactory
			.getLogger(IngestController.class);

	@RequestMapping(value = "/ingest", method = RequestMethod.GET)
	public String list(Model model) {
		String rootUrl = "http://catalog.drexel.edu/coursedescriptions/quarter/undergrad/index.html";
		ArrayList<CollegeListing> colleges = new ArrayList<CollegeListing>();

		try {
			Document document = getUrlDocument(rootUrl);

			Element content = document.getElementById("content");

			NodeList divs = content.getElementsByTagName("div");
			int divCount = divs.getLength();
			for (int i = 0; i < divCount; i++) {
				Element div = (Element) divs.item(i);

				if (div.getAttribute("class").equals("colfloat")) {
					colleges.add(new CollegeListing(div));
				}
			}
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
		} finally {
			model.addAttribute("colleges", colleges);
		}

		return "ingest/list";
	}

	@RequestMapping(value = "/ingest/{collegeCode}/{subjectCode}", method = RequestMethod.GET)
	public String subject(@PathVariable String collegeCode,@PathVariable String subjectCode,Model model) {
		String url ="http://catalog.drexel.edu/coursedescriptions/quarter/undergrad/"+subjectCode.toLowerCase();
		if(subjectCode.equals("UNIV"))
			url+=collegeCode.toLowerCase();
		url+="/";
		
		ArrayList<CourseListing> courses = new ArrayList<CourseListing>();	
		
		try {
			Document document = getUrlDocument(url);

			Element content = document.getElementById("courseinventorycontainer");

			NodeList divs = content.getElementsByTagName("div");
			int divCount = divs.getLength();
			for (int i = 0; i < divCount; i++) {
				Element div = (Element) divs.item(i);

				if (div.getAttribute("class").equals("courseblock")) {
					courses.add(new CourseListing(div));
				}
			}
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
		} finally {
			model.addAttribute("courses", courses);
		}
		
		return "ingest/courses";
	}
	
	private String getFilteredContents(String url) throws IOException {
		URL u = new URL(url);
		BufferedReader input = new BufferedReader(new InputStreamReader(u.openStream()));
		String contents = "";
		String line;
		
		int divs=0;
		while ((line = input.readLine()) != null) {
			if(line.contains("<div")){
				divs++;
			}
			if(line.contains("</div")){
				if(divs==0)
					line=line.replace("</div>", "");
				else
					divs--;
			}
			
			line= line
					.replaceAll(" < ", " &lt; ")
					.replaceAll(" & ", " &amp; ")
					.replaceAll("<img[^>]*>", "")
					.replaceAll("<li[^>]*>", "")
					.replaceAll("</li>", "")
					.replaceAll("<ul[^>]*>", "")
					.replaceAll("</ul>", "")
					.replaceAll("<br/>","")
					;
			
			contents+=line;
		}
		
		return contents;
	}

	private Document getUrlDocument(String url) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			InputSource source = new InputSource(new StringReader(getFilteredContents(url)));
			Document document = builder.parse(source);
			
			return document;
		} catch (ParserConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		} catch (SAXException ex) {
			logger.error(ex.getMessage());
		}

		throw new IllegalArgumentException("Getting document at " + url
				+ " failed.");
	}
}