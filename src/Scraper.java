import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
		ArrayList<TimeSeriesProfile> tProfiles=new ArrayList();
		HashMap<String, String> categorytoFeature = new HashMap();
		BayesClassifierTest classifier = new BayesClassifierTest();
		Map<String, Long> map = new HashMap();
		Map<String, String> jobTitletoCategory = new HashMap<String, String>();
		String learningText;
		String testString;
		Database db = new Database();
		db.connect();
		int count = 0;
		int learningCount = 0;
		String core = "/Users/ollemuronde/Desktop/LINKEDINDATA/US/";
		File folder = new File(core);
		File[] listOfFiles = folder.listFiles();

		Map<String, String> a = initializeJobtoCategory(jobTitletoCategory);

		// jobTitletoCategory= a;

		for (File file : listOfFiles) {
			if (file.isFile()) {
				String filepath = (file.getName());
				File fileTwo = new File(core + filepath);

				Document doc = Jsoup.parse(fileTwo, "UTF-8", "");
				// CURRENET JOB TITLE CLASSIFIER///
				String classification = doc.select("#topcard > div.profile-card.vcard > div.profile-overview > div > p")
						.text();
				classification = trigram(classification);
				classification = stemmer(classification);
				classification = classification.toLowerCase();
				Stopwords filter = new Stopwords();
				// classification = Stopwords.removeStopWords(classification);
				String temp = classification;
				// System.out.println("PRINTING CLASSIFICATION
				// "+""+classification);
				App app = new App(classification);
				String stringObj = new String(classification);
				// System.out.println(stringObj.equals("Tech"));

				if (a.containsKey(classification)) {
				//	System.out.println(classification + "will now become" + jobTitletoCategory.get(classification));
					classification = jobTitletoCategory.get(classification);

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
						
						String educationDurationThird=doc.select("#education > ul > li:nth-child(1) > div > span").text();
						String educationDurationSecond=doc.select("#education > ul > li:nth-child(2) > div > span").text();
						String educationDurationFirst=doc.select("#education > ul > li:nth-child(3) > div > span").text();
						

						// EXPERIENCE INITIALLY IGONRE TIMEFIELDS AND FOCUS ON
						// JOB
						// TRANSITIONS

						String jobPositionFourth = doc.select("#experience > ul > li:nth-child(1) > header > h4 > a")
								.text();
						String jobPositionThird = doc.select("#experience > ul > li:nth-child(2) > header > h4 > a")
								.text();
						String jobPositionSecond = doc.select("#experience > ul > li:nth-child(3) > header > h4 > a")
								.text();
						String jobPositionFirst = doc.select("#experience > ul > li:nth-child(4) > header > h4 > a")
								.text();

						jobPositionFirst = trigram(jobPositionFirst);
						jobPositionSecond = trigram(jobPositionSecond);
						jobPositionThird = trigram(jobPositionThird);
						jobPositionFourth = trigram(jobPositionFourth);
						
						String thirdJobDuration=doc.select("#experience > ul > li:nth-child(1) > div > span").text();
						String  secondJobDuration=doc.select("#experience > ul > li:nth-child(2) > div > span").text();
						String  firstJobDuration=doc.select("#experience > ul > li:nth-child(3) > div > span").text();/// EARLIEST JOB
						// SKILLS
						String skillOne = doc.select("#skills > ul > li:nth-child(2) > a").text();
						String skillTwo = doc.select("#skills > ul > li:nth-child(3) > a").text();
						String skillThree = doc.select("#skills > ul > li:nth-child(4) > a").text();

						learningText = "" + classification + " " + current + " " + previous + " " + university + " "
								+ education_One + " " + education_Two + " " + education_Three + " " + jobPositionFirst
								+ " " + jobPositionSecond + " " + jobPositionThird + " " + jobPositionFourth + " "
								+ skillOne + " " + skillTwo + " " + skillThree;
                    
					  TimeSeriesProfile tProfile=new TimeSeriesProfile(classification, current, previous, university, education_One,
								education_Two, education_Three, jobPositionFirst, jobPositionSecond, jobPositionThird,
								jobPositionFourth, skillOne, skillTwo, skillThree, learningText,firstJobDuration,secondJobDuration,thirdJobDuration,educationDurationFirst,educationDurationSecond,educationDurationThird);
						
						
						Profile profile = new Profile(classification, current, previous, university, education_One,
								education_Two, education_Three, jobPositionFirst, jobPositionSecond, jobPositionThird,
								jobPositionFourth, skillOne, skillTwo, skillThree, learningText);
						if (classification != null && !classification.isEmpty() && learningText != null) {
							count++;
							System.out.println("profile added");
							profiles.add(profile);
							tProfiles.add(tProfile);
						}

					}
				} else {
					System.out.println("Not added");
				}
			}
		}
		
	//	classifier.setUp(profiles);
		db.save(profiles);
		
//		ScraperOneYear oneYearProfiles=new ScraperOneYear(tProfiles);
//		oneYearProfiles.createTimeSeriesProfile(tProfiles);
//		oneYearProfiles.createTimeSeriesProfile(tProfiles);
//		db.saveTimeSeries(oneYearProfiles.createTimeSeriesProfile(tProfiles));
		
		   
		// db.update(profiles);


		
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
		String value = "";
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);
		String text = stem;
		Annotation document = pipeline.process(text);

		for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String lemma = token.get(LemmaAnnotation.class);
				// System.out.println("lemmatized version :" + lemma);
				value = lemma;
			}
		}
		return value;
	}

	public static String stopWords(String word) {
		String value = "";
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, stopword");
		props.setProperty("customAnnotatorClass.stopword", "intoxicant.analytics.coreNlp.StopwordAnnotator");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		String text = word;
		Annotation document = pipeline.process(text);

		for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String wordOne = token.get(TextAnnotation.class);
				String stopWord = token.get(LemmaAnnotation.class);
				// System.out.println("lemmatized version :" + stopWord);
				value = stopWord;
			}

		}
		return value;

	}

	public static Map<String, String> initializeJobtoCategory(Map<String, String> map) {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		map.putIfAbsent("ceo", "Management");
		map.putIfAbsent("manager", "Management");
		map.putIfAbsent("director", "Management");
		map.putIfAbsent("president", "Management");
		map.putIfAbsent("supervisor", "Management");
		map.putIfAbsent("executive", "Management");
		map.putIfAbsent("principal", "Management");
		map.putIfAbsent("chief", "Management");
		map.putIfAbsent("owner", "Management");
		map.putIfAbsent("business", "Management");
		map.putIfAbsent("senior", "Management");
		map.putIfAbsent("official", "Management");
		map.putIfAbsent("mayor", "Management");
		map.putIfAbsent("leader", "Management");
		map.putIfAbsent("law", "LegalSector");
		map.putIfAbsent("judge", "LegalSector");
		map.putIfAbsent("solicitor", "LegalSector");
		map.putIfAbsent("counsel", "LegalSector");
		map.putIfAbsent("founder", "Management");
//		map.putIfAbsent("professor", "EducationSector");
//		map.putIfAbsent("teacher", "EducationSector");
//		map.putIfAbsent("university", "EducationSector");
//		map.putIfAbsent("educator", "EducationSector");
//		map.putIfAbsent("school", "EducationSector");;
		map.putIfAbsent("engineer", "Technology");
		map.putIfAbsent("engineering", "Technology");
		map.putIfAbsent("Tech", "Technology");
		map.putIfAbsent("development", "Technology");
		map.putIfAbsent("consultant", "Management");
		map.putIfAbsent("consulting", "Management");
		return map;
	}
}
