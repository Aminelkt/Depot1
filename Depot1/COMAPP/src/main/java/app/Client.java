package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
	
	public Connection cnx;  
	
	private int ID ;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private int ptsFid;
	
	public Client() {}
	
	public Client(int iD, String nom, String prenom, String email, String password, int ptsFid) {
		super();
		ID = iD;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.ptsFid = ptsFid;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPtsFid() {
		return ptsFid;
	}

	public void setPtsFid(int ptsFid) {
		this.ptsFid = ptsFid;
	}

	@Override
	public String toString() {
		return "Client [ID=" + ID + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password="
				+ password + ", ptsFid=" + ptsFid + "]";
	}
	
	
	public void getConnected(String cnxstr, String user, String pwd) {
	     
        try {
           // cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "123456");     
        	cnx = DriverManager.getConnection(cnxstr, user, pwd);                 
        	System.out.println(" BD Connected"); 
           
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
	
	
	public boolean Login() throws SQLException {
		PreparedStatement st;
        ResultSet res;
        boolean bl = false;
        
        Scanner sc = new Scanner(System.in);
        System.out.println(" Email : "); 
        String em = sc.next();
        System.out.println(" Password : "); 
        String pwd = sc.next();

        //this.getConnected();
        st = cnx.prepareStatement("select * from client where "
        		+ "email = ? and password =?");      
        st.setString(1, em);
        st.setString(2, pwd);

        res = st.executeQuery();  
         
        int c =0;
        while(res.next()){
            c++;
        }
        
        if(c == 0){
            System.out.println("wrong Login/Password");
            bl=false;
        }
        else if(c == 1){
            System.out.println(" Welcome Sir/Madam ");
            bl=true;
        }
           return bl;   
	}
	
	
	public boolean Login(String email, String pwd) throws SQLException {
		PreparedStatement st, st2;
        ResultSet res;
        boolean bl = false;
        
       if(email==null || pwd==null) {
    	   throw new IllegalArgumentException("Email/Password nulls");
       }

       this.getConnected("jdbc:mysql://localhost:3306/gl", "root", "123456");

       st = cnx.prepareStatement("select * from client where "
       		+ "email = ? "); 
       st.setString(1, email);
       res = st.executeQuery();  
       
       int c =0;
       while(res.next()){
           c++;
       }
       
       if(c == 0){
           System.out.println("Client n'existe pas");
           bl=false;
           throw new IllegalArgumentException("Client n'existe pas dans la BD");          
       }
       
       else if(c == 1){
    	   st2 = cnx.prepareStatement("select * from client where "
           		+ "email = ? and password =?");      
           st2.setString(1, email);
           st2.setString(2, pwd);

           res = st2.executeQuery();  
            
          int cn =0;
           while(res.next()){
               cn++;
           }
           
           if(cn == 0){
               System.out.println("wrong Password");
               bl=false;
               throw new IllegalArgumentException("Password Incorrect");
               
           }
           else if(c == 1){
               System.out.println(" Authenticated : Welcome Sir/Madam ");
               bl=true;
           }
              	   
       }
       
       return bl;             
	}
	
	
	
	
	

}
