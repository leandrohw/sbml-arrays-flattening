package io.github.leandrohw;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.arrays.flattening.ArraysFlattening;

public class Runner {

  public static void main(String[] args) {

    if(args.length < 1)
    {
      System.err.println("You need to specify an input file.");
      System.exit(0);
    }
    
    SBMLDocument doc;
    try {
      doc = SBMLReader.read("Input.xml");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, "output.xml", ' ', (short)4);
    } catch (XMLStreamException e) {
      System.err.println("Failed to read input document.");
    } catch (SBMLException e) {
      System.err.println("Failed to read input document.");
    } catch (FileNotFoundException e) {
      System.err.println("Input file could not be found.");
    }
  }
}
