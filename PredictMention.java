/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.classifiers.meta.FilteredClassifier;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
/**
 *
 * @author User
 */
public class PredictMention {
    /**
     * @param args the command line arguments
     */
    
    private Instances trainedData;
    private Instances testData;
    private FilteredClassifier filter;

    public PredictMention() {
        try {
  
            String pathStopList = (new File("SpanishStoplist.txt")).getAbsolutePath();
            filter = new FilteredClassifier();
            filter.setOptions(weka.core.Utils.splitOptions("-F \"weka.filters.unsupervised.attribute.StringToWordVector -R first-last -W 1000 -prune-rate -1.0 -N 0 -stemmer \\\"weka.core.stemmers.SnowballStemmer -S spanish\\\" -stopwords-handler \\\"weka.core.stopwords.WordsFromFile -stopwords "+ pathStopList+"\\\" -M 1 -tokenizer \\\"weka.core.tokenizers.WordTokenizer -delimiters \\\\\\\" \\\\\\\\r\\\\\\\\n\\\\\\\\t.,;:\\\\\\\\\\\\\\'\\\\\\\\\\\\\\\"()?!\\\\\\\"\\\"\" -W weka.classifiers.trees.J48 -- -C 0.25 -M 2"));
        } catch (Exception ex) {
            Logger.getLogger(PredictMention.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    protected void setTrainedData(String path) {
        trainedData = null;
        try {
            DataSource source = new DataSource(path);
            trainedData = source.getDataSet();
            if (trainedData.classIndex() == -1)
                trainedData.setClassIndex(trainedData.numAttributes() - 1);
        } catch (Exception ex) {
            Logger.getLogger(PredictMention.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void setTestData(String path) {
        testData = null;
        try {
            DataSource source = new DataSource(path);
            testData = source.getDataSet();
            if (testData.classIndex() == -1)
                testData.setClassIndex(testData.numAttributes() - 1);
        } catch (Exception ex) {
            Logger.getLogger(PredictMention.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    protected void setTestData(String title, String description, String keywords) {
        testData = new Instances(trainedData);
        testData.clear();
        Instance inst = new DenseInstance(4);
        inst.setDataset(testData);
        inst.setValue(0, title); 
        inst.setValue(1, description); 
        inst.setValue(2, keywords); 
        inst.setMissing(3);
        testData.add(inst);
        
        
    }

    protected void predict(){
         try {
             filter.buildClassifier(trainedData);
             for ( int i = 0; i <  testData.numInstances(); ++i ){
                 double pred = filter.classifyInstance(testData.instance(i));
                 System.out.println("-------------------------------");
                 System.out.println("Instancia " + i);
                 System.out.println("Mención: " + testData.classAttribute().value((int)pred));
             }
         } catch (Exception ex) {
             Logger.getLogger(PredictMention.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
