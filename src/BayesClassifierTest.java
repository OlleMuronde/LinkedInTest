
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;



public class BayesClassifierTest {

	

	private static final double EPSILON = 0.001;
	private static final String CATEGORY_NEGATIVE = "negative";
	private static final String CATEGORY_POSITIVE = "positive";
	private String category;
	private static int count=0;
	public String test;
	
	private Classifier<String, String> bayes;
	
	
	public BayesClassifierTest(){
	
	}

	public void setUp(HashMap<String,String>map) {
		bayes = new BayesClassifier<String, String>();
		bayes.setMemoryCapacity(20000);
		for (HashMap.Entry<String, String> entry : map.entrySet()) {
		    String category = entry.getKey();
		    String learningText = entry.getValue();
		//  System.out.println("the features are "+learningText);
         String[] learningTextArray=learningText.split("\\s");
       // System.out.println("Learning array fed ccnverted to list and fed to learn method "+Arrays.toString(learningTextArray));
        bayes.learn(category, Arrays.asList(learningTextArray));
    	
		}
	//	System.out.println(bayes.getCategories());

		 String[] unknownText1 = "Deputy Commissioner".split("\\s");
		 
         String result= bayes.classify(Arrays.asList(unknownText1)).getCategory();
      //   System.out.println("THE FINAL RESULT IS"+result);
	}
	

	public void testStringClassificationInDetails(String unknown) {
		
		final String[] unknownText1 = unknown.split("\\s");
		
		Collection<Classification<String, String>> classifications = ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));
		
		List<Classification<String, String>> list = new ArrayList<Classification<String,String>>(classifications);
		
		Assert.assertEquals(CATEGORY_NEGATIVE, list.get(0).getCategory());
		Assert.assertEquals(0.0078125, list.get(0).getProbability(), EPSILON);
		
		Assert.assertEquals(CATEGORY_POSITIVE, list.get(1).getCategory());
		Assert.assertEquals(0.0234375, list.get(1).getProbability(), EPSILON);
	}

}