package rp.warehouse.pc.selection;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CSV2Arff {
 /**
  * takes 2 arguments:
  * - CSV input file
  * - ARFF output file
  */
 public static void main(String[] args) throws Exception {

   // load CSV
   CSVLoader loader = new CSVLoader();
   loader.setSource(new File("./training_jobs.csv"));
   Instances data = loader.getDataSet();

   // save ARFF
   ArffSaver saver = new ArffSaver();
   saver.setInstances(data);
   saver.setFile(new File("./test.arff"));
   //saver.setDestination(new File(args[1]));
   saver.writeBatch();
 }
}