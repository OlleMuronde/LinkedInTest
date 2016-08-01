package Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;

public class Test {

  public static void main(String[] args) throws IOException {
	        String stem="cats at my bats";
		    stemmer(stem);
		    stopWords(stem);
	        
}
  public static String stemmer(String stem) {
		String value="";
		Properties props = new Properties(); 
      props.put("annotators", "tokenize, ssplit, pos, lemma"); 
      StanfordCoreNLP  pipeline = new StanfordCoreNLP(props, false);
      String text = stem; 
      Annotation document = pipeline.process(text);  

      for(CoreMap sentence: document.get(SentencesAnnotation.class))
      {    
          for(CoreLabel token: sentence.get(TokensAnnotation.class))
          {       
              String word = token.get(TextAnnotation.class);      
              String lemma = token.get(LemmaAnnotation.class);
             value= lemma;
          }
          
      }
      System.out.println("lemmatized version :" + value);
      return value;
  }
  

  public static String stopWords(String word){
	  String value="";
	  Properties props = new Properties();
	   props.put("annotators", "tokenize, ssplit, stopword");
	   props.setProperty("customAnnotatorClass.stopword", "intoxicant.analytics.coreNlp.StopwordAnnotator");
      StanfordCoreNLP  pipeline = new StanfordCoreNLP(props);
      String text = word; 
      Annotation document = pipeline.process(text);  

      for(CoreMap sentence: document.get(SentencesAnnotation.class))
      {    
          for(CoreLabel token: sentence.get(TokensAnnotation.class))
          {       
              String wordOne = token.get(TextAnnotation.class);      
              String stopWord = token.get(LemmaAnnotation.class); 
              System.out.println("lemmatized version :" + stopWord);
             value= stopWord;
          }
          
      }
      System.out.println(" stop version :" + value);
      return value;
	  
  }
}