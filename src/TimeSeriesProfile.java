
public class TimeSeriesProfile {
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
	private String  learningText;
	private String firstJobDuration;
	private String secondJobDuration;
	private String thirdJobDuration;
	private String educationDurationFirst;
	private String educationDurationSecond;
	private String educationDurationThird;

	public String getLearningText() {
		return learningText;
	}

	public void setLearningText(String learningText) {
		this.learningText = learningText;
	}

	public TimeSeriesProfile( String classification, String current_Job, String previous_Job, String university,
			String education_One, String education_Two, String education_Three, String experience_One,
			String experience_Two, String experience_Three, String experience_Four, String skills_One,
			String skills_Two, String skills_Three,String learningText, String firstJobDuration, String secondJobDuration, String thirdJobDuration, String educationDurationFirst, String educationDurationSecond, String educationDurationThird) {
	
		this.classification = classification;
		this.current_Job = current_Job;
		this.previous_Job = previous_Job;
		this.university = university;
		this.education_One = education_One;
		this.education_Two = education_Two;
		this.education_Three = education_Three;
		this.experience_One = experience_One;
		this.experience_Two = experience_Two;
		this.experience_Three = experience_Three;
		this.experience_Four = experience_Four;
		this.skills_One = skills_One;
		this.skills_Two = skills_Two;
		this.skills_Three = skills_Three;
		this.learningText=learningText;
		this.firstJobDuration=firstJobDuration;
		this.secondJobDuration=secondJobDuration;
		this.thirdJobDuration=thirdJobDuration;
		this.educationDurationFirst=educationDurationFirst;
		this.educationDurationSecond=educationDurationSecond;
		this.educationDurationThird=educationDurationThird;
		
		
	}


	public String getFirstJobDuration() {
		return firstJobDuration;
	}

	public void setFirstJobDuration(String firstJobDuration) {
		this.firstJobDuration = firstJobDuration;
	}

	public String getSecondJobDuration() {
		return secondJobDuration;
	}

	public void setSecondJobDuration(String secondJobDuration) {
		this.secondJobDuration = secondJobDuration;
	}

	public String getThirdJobDuration() {
		return thirdJobDuration;
	}

	public void setThirdJobDuration(String thirdJobDuration) {
		this.thirdJobDuration = thirdJobDuration;
	}

	public String getEducationDurationFirst() {
		return educationDurationFirst;
	}

	public void setEducationDurationFirst(String educationDurationFirst) {
		this.educationDurationFirst = educationDurationFirst;
	}

	public String getEducationDurationSecond() {
		return educationDurationSecond;
	}

	public void setEducationDurationSecond(String educationDurationSecond) {
		this.educationDurationSecond = educationDurationSecond;
	}

	public String getEducationDurationThird() {
		return educationDurationThird;
	}

	public void setEducationDurationThird(String educationDurationThird) {
		this.educationDurationThird = educationDurationThird;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getCurrent_Job() {
		return current_Job;
	}

	public void setCurrent_Job(String current_Job) {
		this.current_Job = current_Job;
	}

	public String getPrevious_Job() {
		return previous_Job;
	}

	public void setPrevious_Job(String previous_Job) {
		this.previous_Job = previous_Job;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getEducation_One() {
		return education_One;
	}

	public void setEducation_One(String education_One) {
		this.education_One = education_One;
	}

	public String getEducation_Two() {
		return education_Two;
	}

	public void setEducation_Two(String education_Two) {
		this.education_Two = education_Two;
	}

	public String getEducation_Three() {
		return education_Three;
	}

	public void setEducation_Three(String education_Three) {
		this.education_Three = education_Three;
	}

	public String getExperience_One() {
		return experience_One;
	}

	public void setExperience_One(String experience_One) {
		this.experience_One = experience_One;
	}

	public String getExperience_Two() {
		return experience_Two;
	}

	public void setExperience_Two(String experience_Two) {
		this.experience_Two = experience_Two;
	}

	public String getExperience_Three() {
		return experience_Three;
	}

	public void setExperience_Three(String experience_Three) {
		this.experience_Three = experience_Three;
	}

	public String getExperience_Four() {
		return experience_Four;
	}

	public void setExperience_Four(String experience_Four) {
		this.experience_Four = experience_Four;
	}

	public String getSkills_One() {
		return skills_One;
	}

	public void setSkills_One(String skills_One) {
		this.skills_One = skills_One;
	}

	public String getSkills_Two() {
		return skills_Two;
	}

	public void setSkills_Two(String skills_Two) {
		this.skills_Two = skills_Two;
	}

	public String getSkills_Three() {
		return skills_Three;
	}

	public void setSkills_Three(String skills_Three) {
		this.skills_Three = skills_Three;
	}

	public String getSkills_Four() {
		return skills_Four;
	}

	public void setSkills_Four(String skills_Four) {
		this.skills_Four = skills_Four;
	}
	
}
