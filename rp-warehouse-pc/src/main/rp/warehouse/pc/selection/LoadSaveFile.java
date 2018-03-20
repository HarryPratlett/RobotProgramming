package rp.warehouse.pc.selection;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;

import weka.core.converters.ConverterUtils.DataSource;
public class LoadSaveFile{
    public static void main(String args[]) throws Exception{
        DataSource source = new DataSource("./house.arff");
        Instances dataset = source.getDataSet();
        //Instances dataset = new Instances(new BufferedReader(new FileReader("/home/likewise-open/ACADEMIC/csstnns/test/house.arff")));        
        
        System.out.println(dataset.toSummaryString());
        
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataset);
        saver.setFile(new File("./new.arff"));
        saver.writeBatch();
    }
}