import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

///WRITE ABOUT MISSING VALUES IN ALGORITHM
public class Scraper {
	public static void main(String[] args) throws IOException, SQLException {
		ArrayList<Profile> profiles = new ArrayList();
		HashMap<String, String> categorytoFeature = new HashMap();
		BayesClassifierTest classifier = new BayesClassifierTest();
		Map<String, Long> map = new HashMap();

		String learningText;
		String testString;
		Database db = new Database();
		db.connect();
		int count = 0;
		int learningCount = 0;
		String learningTest;
		String core = "/Users/ollemuronde/Desktop/LINKEDINDATA/site=linkedin.com/";
		File folder = new File(core);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				String filepath = (file.getName());
				File fileTwo = new File(core + filepath);

				Document doc = Jsoup.parse(fileTwo, "UTF-8", "");
				// CURRENET JOB TITLE CLASSIFIER///
				String classification = doc.select("#topcard > div.profile-card.vcard > div.profile-overview > div > p")
						.text();
				classification = trigram(classification);
				classification=stemmer(classification);
				String temp = classification;
				
                 
				String[] tempArray = temp.split("\\W+");
				for (String t : tempArray) {
					if (!map.containsKey(t)) {
						map.put(t, (long) 1);
					} else {
						map.put(t, map.get(t) + 1);
					}

					// SCRAPPING THE INITIAL PROFILE current previous and
					// university
					String current, previous, university, currentTwo, previousTwo, universityTwo;
					current = doc
							.select("#topcard > div.profile-card.vcard > div.profile-overview > div > table > tbody > tr:nth-child(1) > td > ol > li > span")
							.text();
					previous = doc
							.select("#topcard > div.profile-card.vcard > div.profile-overview > div > table > tbody > tr:nth-child(2) > td > ol > li:nth-child(1) > a")
							.text();
					university = doc
							.select("#topcard > div.profile-card.vcard > div.profile-overview > div > table > tbody > tr:nth-child(3) > td > ol > li > a")
							.text();

					/// EDUCATION
					String education_Three = doc.select("#education > ul > li:nth-child(1)").text();
					;
					String education_Two = doc.select("#education > ul > li:nth-child(2)").text();
					String education_One = doc.select("#education > ul > li:nth-child(3)").text();
					education_One = trigram(education_One);
					education_Two = trigram(education_Two);
					education_Three = trigram(education_Three);

					// EXPERIENCE INITIALLY IGONRE TIMEFIELDS AND FOCUS ON JOB
					// TRANSITIONS

					String jobPositionFourth = doc.select("#experience > ul > li:nth-child(1) > header > h4 > a")
							.text();
					String jobPositionThird = doc.select("#experience > ul > li:nth-child(2) > header > h4 > a").text();
					String jobPositionSecond = doc.select("#experience > ul > li:nth-child(3) > header > h4 > a")
							.text();
					String jobPositionFirst = doc.select("#experience > ul > li:nth-child(4) > header > h4 > a").text();

					jobPositionFirst = trigram(jobPositionFirst);
					jobPositionSecond = trigram(jobPositionSecond);
					jobPositionThird = trigram(jobPositionThird);
					jobPositionFourth = trigram(jobPositionFourth);

					// SKILLS
					String skillOne = doc.select("#skills > ul > li:nth-child(2) > a").text();
					String skillTwo = doc.select("#skills > ul > li:nth-child(3) > a").text();
					String skillThree = doc.select("#skills > ul > li:nth-child(4) > a").text();

					learningText = "" + classification + "" + current + "" + previous + "" + university + ""
							+ education_One + "" + education_Two + "" + education_Three + "" + jobPositionFirst + ""
							+ jobPositionSecond + "" + jobPositionThird + "" + jobPositionFourth + "" + skillOne + ""
							+ skillTwo + "" + skillThree;
					if (classification != null && !classification.isEmpty() && learningText != null) {
						categorytoFeature.put(classification, learningText);
					}

					// if(learningCount==13202){
					// classifier.test=learningText;
					// System.out.println("THE THING WE ARE GONNA TEST FOR
					// IS"+classifier.test);
					// }

					// System.out.println("");
					// System.out.println("Job title is " + "" +
					// classification);
					// System.out.println("Current title is " + "" + current);
					// System.out.println("Previous title is " + "" + previous);
					// System.out.println("University Attended is " + "" +
					// university);
					// System.out.println("First university was " + "" +
					// education_One);
					// System.out.println("Second university was " + "" +
					// education_Two);
					// System.out.println("Third university was " + "" +
					// education_Three);
					// System.out.println("First job was " + "" +
					// jobPositionFirst);
					// System.out.println("Second job was " + "" +
					// jobPositionSecond);
					// System.out.println("Third job was " + "" +
					// jobPositionThird);
					// System.out.println("Fourth job was " + "" +
					// jobPositionFourth);
					// System.out.println("First skill was " + skillOne);
					// System.out.println("Second skill was " + skillTwo);
					// System.out.println("Third skill was " + skillThree);
					//
					// String[] arrayThree=education_Three.split("\\s");
					// StringBuilder stringBuilderThree = new StringBuilder();
					// if(arrayThree.length>=3){
					// for (int i=0; i<3; i++){
					// arrayThree[i]=" "+arrayThree[i];
					// stringBuilderThree.append(arrayThree[i]);
					// }
					// String tempThree=stringBuilderThree.toString();
					// System.out.println(tempThree);
					// }
					//
					//
					// String[] arrayTwo=education_Two.split("\\s");
					// StringBuilder stringBuilderTwo = new StringBuilder();
					// if(arrayTwo.length>=3){
					// for (int i=0; i<3; i++){
					// arrayTwo[i]=" "+arrayTwo[i];
					// stringBuilderTwo.append(arrayTwo[i]);
					// }
					// String tempTwo=stringBuilderTwo.toString();
					// System.out.println(tempTwo);
					// }
					//
					// String[] array=education_One.split("\\s");
					// StringBuilder stringBuilder = new StringBuilder();
					// if(array.length>=3){
					// for (int i=0; i<3; i++){
					// array[i]=" "+array[i];
					// stringBuilder.append(array[i]);
					// }
					// String temp=stringBuilder.toString();
					// System.out.println(temp);
					// }

					Profile profile = new Profile(count, classification, current, previous, university, education_One,
							education_Two, education_Three, jobPositionFirst, jobPositionSecond, jobPositionThird,
							jobPositionFourth, skillOne, skillTwo, skillThree);
					if (profile.getClassification() != null && !profile.getClassification().isEmpty()) {
						count++;
						profiles.add(profile);
						profile.setId(count);
					}

				}

			}
		}
		classifier.setUp(categorytoFeature);
		map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
		for (Map.Entry<String, Long> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			Long value = entry.getValue();
			// System.out.println("key, " + key + " value " + value);
		}
		// Map<String, Integer> sortingUsingGuava =
		// Maps.newTreeMap(Ordering.natural());
		// sortingUsingGuava.putAll(map);
		// classifier.testStringClassification("harvard university");

		// db.update(profiles);
		// for(Profile prof:profiles){
		// db.save(profiles);
		// int id= prof.getId();
		// String classification= prof.getClassification();
		// String current= prof.getCurrent_Job();
		// String previous= prof.getPrevious_Job();
		// String university= prof.getUniversity();
		// String education_One= prof.getEducation_One();
		// String education_Two= prof.getEducation_Two();
		// String education_Three= prof.getEducation_Three();
		// String job_One= prof.getExperience_One();
		// String job_Two= prof.getEducation_Two();
		// String job_Three= prof.getExperience_Three();
		// String job_Four= prof.getExperience_Four();
		// String skills_One=prof.getSkills_One();
		// String skills_Two=prof.getSkills_Two();
		// String skills_Three=prof.getSkills_Three();
	}

	public static String trigram(String value) {
		String[] array = value.split("\\s");
		StringBuilder stringBuilder = new StringBuilder();
		if (array.length >= 3) {
			for (int i = 0; i < 3; i++) {
				array[i] = " " + array[i];
				stringBuilder.append(array[i]);
			}
			String temp = stringBuilder.toString();
			value = temp;
		}
		// System.out.println(value);
		return value;
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
                System.out.println("lemmatized version :" + lemma);
               value= lemma;
            }
            
        }
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
      return value;
	  
  }
}	
	

