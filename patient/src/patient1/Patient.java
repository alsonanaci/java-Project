package patient1;

import java.io.Serializable;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.Statement;  
import java.sql.ResultSet;  
import java.util.ArrayList;  
import java.util.Map;  
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.RequestScoped;  
import javax.faces.context.FacesContext;  
@ManagedBean  
@RequestScoped  
public class Patient implements Serializable {

    private int patient_id;
	private String pname;
	private String cel;
	private String email;
	private String gjinia;
	private String adresa;
	private String diagnoza;
	
	ArrayList<Patient> patientsList ;
	
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  
	Connection connection;  
	
	

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGjinia() {
		return gjinia;
	}

	public void setGjinia(String gjinia) {
		this.gjinia = gjinia;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getDiagnoza() {
		return diagnoza;
	}

	public void setDiagnoza(String diagnoza) {
		this.diagnoza = diagnoza;
	}
	
	// Used to establish connection
	
	public Connection getConnection(){  
		try{  
		Class.forName("com.mysql.jdbc.Driver");     
		connection = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/prova1?serverTimezone=UTC", "root","1234");  
		}catch(Exception e){  
		System.out.println(e);  
		}  
		return connection;  
	}
	public ArrayList<Patient> patientsList(){  
		try{  
		 patientsList = new ArrayList<Patient>();  
		connection = getConnection();  
		Statement stmt=getConnection().createStatement();    
		ResultSet rs=stmt.executeQuery("select * from patient");    
		while(rs.next()){  
		Patient  patient  = new Patient();  
		patient.setPatient_id(rs.getInt("patient_id"));  
		patient.setPname(rs.getString("pname"));      
		patient.setCel(rs.getString("cel"));    
		patient.setEmail(rs.getString("email")); 
		patient.setGjinia(rs.getString("gjinia")); 
		patient.setAdresa(rs.getString("adresa"));
		patient.setDiagnoza(rs.getString("diagnoza"));
		patientsList.add(patient);  
		}  
		connection.close();          
		}catch(Exception e){  
		System.out.println(e);  
		}  
		return patientsList;  
		}  
	
	// Used to save patient record  
	
	public String save(){  
		int result = 0;  
		try{  
		connection = getConnection();  
		PreparedStatement stmt = connection.prepareStatement(  
		"insert into patient(pname,cel,email,gjinia,adresa,diagnoza) values(?,?,?,?,?,?)");  
		 
		stmt.setString(1, pname); 
		stmt.setString(2, cel);  
		stmt.setString(3, email);
		stmt.setString(4, gjinia);
		stmt.setString(5, adresa);
		stmt.setString(6, diagnoza);
		result = stmt.executeUpdate();  
		connection.close();  
		}catch(Exception e){  
		System.out.println(e);  
		}  
		if(result !=0)  
		return "index.xhtml?faces-redirect=true";  
		else return "create.xhtml?faces-redirect=true";  
		}  
	
	// Used to fetch record to update  
	public String edit (int patient_id){  
	Patient patient = null;  
	System.out.println(patient_id); 
	
	try{  
	connection = getConnection();  
	Statement stmt=getConnection().createStatement();    
	ResultSet rs=stmt.executeQuery("select * from patient where patient_id = " + patient_id);  
	rs.next();  
	patient = new Patient();  
	patient.setPatient_id(rs.getInt("patient_id"));  
	patient.setPname(rs.getString("pname"));      
	patient.setCel(rs.getString("cel"));    
	patient.setEmail(rs.getString("email")); 
	patient.setGjinia(rs.getString("gjinia")); 
	patient.setAdresa(rs.getString("adresa"));
	patient.setDiagnoza(rs.getString("diagnoza")); 
	sessionMap.put("editPatient", patient);  
	connection.close();  
	}catch(Exception e){  
	System.out.println(e);  
	}         
	return "/edit.xhtml?faces-redirect=true";  
	} 
	
	// Used to update patient record 
	
	public String update(Patient p){  
		
	
	try{  
	connection = getConnection();    
	PreparedStatement stmt=connection.prepareStatement("update patient set pname=?,cel=?,email=?,gjinia=?,adresa=?,diagnoza=? where patient_id=?");    
	 
	stmt.setString(1, p.getPname()); 
	stmt.setString(2, p.getCel());  
	stmt.setString(3, p.getEmail());
	stmt.setString(4, p.getGjinia());
	stmt.setString(5, p.getAdresa());
	stmt.setString(6, p.getDiagnoza()); 
	stmt.setInt(7, p.getPatient_id());
	
	stmt.executeUpdate();  
	
	connection.close();  
	}catch(Exception e){  
	System.out.println();  
	}  
	return "/index.xhtml?faces-redirect=true";        
	}  
	// Used to delete user record  
	public void delete(int patient_id){  
	try{  
	connection = getConnection();    
	PreparedStatement stmt = connection.prepareStatement("delete from patient where patient_id = " + (patient_id));    
	stmt.executeUpdate();    
	}catch(Exception e){  
	System.out.println(e);  
	}  
	} 
	
	
}