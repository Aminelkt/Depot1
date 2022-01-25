import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;

import app.Client;

public class ClientTest {

	static Client cl = new Client();
	Connection cnx = cl.cnx;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cl.getConnected("jdbc:mysql://localhost:3306/gl", "root", "123456");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		cl.cnx.close();
	}

	@BeforeEach
	@Test
	public void ConnectionNullTest() {
			assertFalse(cnx==null);
	}
	
	@Test
	public void ConnectionClosedTest() {
			try {
				assertFalse(cnx.isClosed());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	@Test
	public void testGetConnected() {
		
	}


	@Test
	@DisplayName("Authentification Réussie")
	@Timeout(value = 1, unit = TimeUnit.MILLISECONDS)
	public void testLogin() {
		
		/*Scanner sc = new Scanner(System.in);
        System.out.println(" Email : "); 
        String em = sc.next();
        System.out.println(" Password : "); 
        String pwd = sc.next();*/
		
		try {
			assertTrue(cl.Login("test@gmail.com", "1234"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	@DisplayName("Paramétres Nulls")
	public void NullParamLoginTest() {
				
		try {
			assertFalse(cl.Login(null, null));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Exception Param Nulls")
	public void NullParamException() {
		assertThrows(IllegalArgumentException.class, ()->{cl.Login(null, null);}," Exception attendue mais non levée");
	}
	
	
	
	@Test
	@DisplayName("Client Inconnu")
	public void UnkownLoginTest() {
		
				
		try {
			assertFalse(cl.Login("hd@gmail.com", "01011"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Unkown Client Exception")
	public void UnkownClientException() {
		assertThrows(IllegalArgumentException.class, ()->{cl.Login("hd@gmail.com", "01011");}," Exception attendue mais non levée");
	}
	
	
	
	@Test
	@DisplayName("Password Incorrect")
	public void WrongPwdLoginTest() {
						
		try {
			assertFalse(cl.Login("test@gmail.com", "abcd"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	@DisplayName("Wrong PWD Exception")
	public void WrongPwdException() {
		assertThrows(IllegalArgumentException.class, ()->{cl.Login("test@gmail.com", "abcd");}," Exception attendue mais non levée");
	}
	

}
