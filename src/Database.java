
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


	public class Database {
		Connection con;
		int count=0;
	public void connect(){
		
	try {
		Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Database method running with no issues");
	} catch (ClassNotFoundException e) {
		System.out.println("Driver not found");
		e.printStackTrace();
	}


	String url="jdbc:mysql://localhost:3306/LinkedIn";
	String user="root";
	String password="Rundi001";

    
	try {
		con= DriverManager.getConnection(url, user, password);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	public void disconnect(){
		
	}
	
	
	public void save(ArrayList<Profile> profiles ) throws SQLException{
//	PreparedStatement statement= con.prepareStatement("checkSql");
//	String checkSql="select count(*) as count from LinkedIn where id=?";
	///FINISH OFFF !!!!!!!!
	
	String insertSql="insert into LINKEDIN(id,classification,current,previous,university,education_One,education_Two,education_Three,job_One,job_Two,job_Three,job_Four,skills_One,skills_Two,skills_Three) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	PreparedStatement insertStatement=con.prepareStatement(insertSql);
	
	
	
	for(Profile prof:profiles){
	int col=1;
	insertStatement.setInt(col++,prof.getId());
	insertStatement.setString(col++,prof.getClassification());
	insertStatement.setString(col++,prof.getCurrent_Job());
	insertStatement.setString(col++,prof.getPrevious_Job());
	insertStatement.setString(col++,prof.getUniversity());
	insertStatement.setString(col++,prof.getEducation_One());
	insertStatement.setString(col++,prof.getEducation_Two());
	insertStatement.setString(col++,prof.getEducation_Three());
	insertStatement.setString(col++,prof.getExperience_One());
	insertStatement.setString(col++,prof.getExperience_Two());
	insertStatement.setString(col++,prof.getEducation_Three());
	insertStatement.setString(col++,prof.getExperience_Four());
	insertStatement.setString(col++,prof.getSkills_One());
	insertStatement.setString(col++,prof.getSkills_Two());
	insertStatement.setString(col++,prof.getSkills_Three());
	insertStatement.executeUpdate();
	}
	insertStatement.close();
	
	}
	public void update(ArrayList<Profile>profiles) throws SQLException{
		String updateSql="update LINKEDIN set classification=?,current=?,previous=?,university=?,education_One=?,education_Two=?,education_Three=?,job_One=?,job_Two=?,job_Three=?,job_Four=?,skills_One=?,skills_Two=?,skills_Three=? where id=?";
		PreparedStatement updateStatement=con.prepareStatement(updateSql);
		for(Profile prof:profiles){
		int col=1;
		
		updateStatement.setString(col++,prof.getClassification());
		updateStatement.setString(col++,prof.getCurrent_Job());
		updateStatement.setString(col++,prof.getPrevious_Job());
		updateStatement.setString(col++,prof.getUniversity());
		updateStatement.setString(col++,prof.getEducation_One());
		updateStatement.setString(col++,prof.getEducation_Two());
		updateStatement.setString(col++,prof.getEducation_Three());
		updateStatement.setString(col++,prof.getExperience_One());
		updateStatement.setString(col++,prof.getExperience_Two());
		updateStatement.setString(col++,prof.getEducation_Three());
		updateStatement.setString(col++,prof.getExperience_Four());
		updateStatement.setString(col++,prof.getSkills_One());
		updateStatement.setString(col++,prof.getSkills_Two());
		updateStatement.setString(col++,prof.getSkills_Three());
		updateStatement.setInt(col++,prof.getId());
		updateStatement.executeUpdate();
		System.out.println("Profile num"+ count);
		count++;
		}
		updateStatement.close();
	}
	
	
	}
	
	

