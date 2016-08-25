import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

public class ScraperOneYear {
	private String classification;
	private String current_Job;
	private String previous_Job;
	private String university;
	private String education_One;
	private String education_Two;
	private String education_Three;
	private String experience_One;
	private String experience_Two;
	private String experience_Three;
	private String experience_Four;
	private String skills_One;
	private String skills_Two;
	private String skills_Three;
	private String skills_Four;
	private String learningText;
	private String firstJobDuration;
	private String secondJobDuration;
	private String thirdJobDuration;
	private String educationDurationFirst;
	private String educationDurationSecond;
	private String educationDurationThird;
	private static int profileCount=0;

	/// CHECK IF STATEMENTS MOST PROFILES NOT CLEARING THEM
	

	// stringTwo.substring(stringTwo.lastIndexOf('–') + 1); MAGIC
	// stringThree.substring(Math.max(0, stringThree.length() - 4));better magic

	public ScraperOneYear(ArrayList<TimeSeriesProfile> profiles) {

	}

	public ArrayList<TimeSeriesProfile> createTimeSeriesProfile(ArrayList<TimeSeriesProfile> profiles) {
        
		for (TimeSeriesProfile profile : profiles) {
			String stringOne = profile.getEducationDurationFirst();
			String stringTwo = profile.getEducationDurationSecond();
			String stringThree = profile.getEducationDurationThird();

			if (profile.getUniversity().equals(profile.getEducation_One().trim())
					&& !profile.getUniversity().isEmpty()) {
				if (stringOne != null && !stringOne.isEmpty()) {
					stringOne = returnYearOfInterest(stringOne);
					RangeChecker(stringOne, profile);
				}
			} else if (profile.getUniversity().equals(profile.getEducation_Two().trim())
					&& !profile.getUniversity().isEmpty()) {
				if (stringTwo != null && !stringTwo.isEmpty()) {
					stringTwo = returnYearOfInterest(stringTwo);
					RangeChecker(stringTwo, profile);
				}
			} else if (profile.getUniversity().equals(profile.getEducation_Three().trim())
					&& !profile.getUniversity().isEmpty()) {

				if (stringThree != null && !stringThree.isEmpty()) {
					stringThree = returnYearOfInterest(stringThree);
					RangeChecker(stringThree, profile);
				}
			}
			
		}                            ///////FIX SO YOU RETURN ONLY ELIGIBLE MAPS
		return profiles;
	}

	public void RangeChecker(String year, TimeSeriesProfile profile) {
		if (!year.matches(".*(\\b\\d{4}\\b)")){
			year="3000";
		}
		int graduationYear = Integer.parseInt(year);
		int firstJobStartYear = Integer.parseInt(returnJobDurationYear(profile.getFirstJobDuration()));
		int secondJobStartYear = Integer.parseInt(returnJobDurationYear(profile.getSecondJobDuration()));
		int thirdJobStartYear = Integer.parseInt(returnJobDurationYear(profile.getThirdJobDuration()));

		if (graduationYear == firstJobStartYear || (graduationYear + 1) == firstJobStartYear
				|| (graduationYear + 2) == firstJobStartYear) {
			profile.setClassification(profile.getExperience_One());
			profile.setExperience_Two("unknown");
			profile.setExperience_Three("unknown");
			profile.setExperience_Four("unknown");
			profile.setEducationDurationSecond("unkwown");
			profile.setEducationDurationSecond("third");			
			profileCount++;
			System.out.println("PROFILE CHANGED, count is now "+ profileCount );
		} else if (graduationYear == secondJobStartYear||(graduationYear+1)==secondJobStartYear||(graduationYear+2)==secondJobStartYear) {
			profile.setClassification(profile.getExperience_Two());
			profile.setExperience_Three("unknown");
			profile.setExperience_Four("unknown");
			profile.setEducationDurationThird("unkwown");
			
			
			profileCount++;
			System.out.println("PROFILE CHANGED, count is now "+ profileCount );
		} else if (graduationYear == thirdJobStartYear||(graduationYear+1)==thirdJobStartYear||(graduationYear+2)==thirdJobStartYear) {
			profile.setClassification(profile.getExperience_Three());
			profile.setExperience_Four("unknown");
			profileCount++;
			System.out.println("PROFILE CHANGED, count is now "+ profileCount );
		}

	}

	public static String returnYearOfInterest(String year) {
		year = year.substring(Math.max(0, year.length() - 4));
		return year;

	}

	public static String returnJobDurationYear(String jobDuration) {
		try{
			Pattern regex = Pattern.compile(".*(\\b\\d{4}\\b)");
			String result = "";
			Matcher m = regex.matcher(jobDuration);
			if (m.find()) {
				result = m.group(1);
				System.out.println("returning result");
				jobDuration=result;
				return jobDuration;
				}
		}catch(NumberFormatException ex){ // handle your exception
        return "3000";
		}
		return "3000";
	}

}