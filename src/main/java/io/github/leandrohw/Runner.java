package io.github.leandrohw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.arrays.flattening.ArraysFlattening;
import org.sbml.jsbml.ext.arrays.validator.ArraysValidator;

public class Runner {

  public static void main(String[] args) {

    boolean check = false;
    int numErrors = 0;
    String filename = null;
    
    if(args.length == 1)
    {
      if(args[0].endsWith(".xml"))
      {
        filename = args[0];
      }
    }
    else if(args.length == 2)
    {
      if(args[0].equals("-c"))
      {
        check = true;
      }
      if(args[1].endsWith(".xml"))
      {
        filename = args[1];
      }
    }
    
    if(filename == null)
    {
      System.err.println("You need to specify an input file.");
      System.exit(0);
    }
    
    
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(new File(filename));
      
      if(check)
      {
        List<SBMLError> arraysErrors = ArraysValidator.validate(doc);
        numErrors = arraysErrors.size();
        for(SBMLError error : arraysErrors)
        {
          System.out.println(error.getMessage());
        }
      }
      
      if(numErrors == 0)
      {
        SBMLDocument flattened = ArraysFlattening.convert(doc);
        SBMLWriter.write(flattened, "output.xml", ' ', (short)4);
      }
      else
      {
        System.out.println("Skipping flattening because " + numErrors + " errors were found.");
      }
    } catch (XMLStreamException e) {
      System.err.println("Failed to parse XML document.");
      System.err.println(e.getMessage());
    } catch (SBMLException e) {
      System.err.println("Failed to read input document.");
      System.err.println(e.getShortMessage());
    } catch (FileNotFoundException e) {
      System.err.println("Input file could not be found.");
    } catch (IOException e) {
      System.err.println("Failed to read input document.");
      System.err.println(e.getMessage());
    }
  }
}
