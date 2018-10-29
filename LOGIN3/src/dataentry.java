import java.io.IOException;
import java.sql.*;
import static java.lang.System.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;
//import java.security.MessageDigest;
//import java.util.Arrays;
//import javax.swing.JOptionPane;

//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet("/dataentry")
public class dataentry extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
       
   
    public dataentry() {
        super();
        }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("Name");
		String regno = request.getParameter("regno");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		String encrt = request.getParameter("encryptiontype");
		
		if(encrt.equals("bf"))
		{
			try {
				String pass;
				password = bfish(password);
				System.out.println("/n"+password+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		else if(encrt.equals("aes"))
		{
			try {
				password = aes(password);
				System.out.println("\n"+password+"\n");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		else if(encrt.equals("tdes"))
		{
			try {
				password = tdes(password);
				System.out.println("\n"+password+"\n");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root","Kaushambi123");
		Statement st=conn.createStatement();
		Statement st2=conn.createStatement();
		HttpSession session=request.getSession();
		
		st.executeUpdate("insert into user432 values('"+name+"','"+regno+"','"+password+"','"+usertype+"')");
		st2.executeUpdate("insert into encr values('"+regno+"','"+encrt+"')");
		session.setAttribute("encryption_type",encrt);
		session.setAttribute("registerno",regno);
		session.setAttribute("usertype", usertype); 
		session.setAttribute("name",name);
		response.sendRedirect("welcome.jsp");
		//request.getRequestDispatcher("welcome.jsp").forward(request, response);
 
		//out.println("Data is successfully inserted!");
		}
		catch(Exception e)
		{
		System.out.print(e);
		e.printStackTrace();
		}
		//doGet(request, response);
		
	}
		

											/* 				BLOWFISH    			*/
	
public static String bfish(String a) throws Exception {    
    KeyGenerator keygenerator = KeyGenerator.getInstance("Blowfish");    
    SecretKey secretkey = keygenerator.generateKey();    
    Cipher cipher = Cipher.getInstance("Blowfish");    
    cipher.init(Cipher.ENCRYPT_MODE, secretkey);
    String inputText = a;   
    byte[] encrypted = cipher.doFinal(inputText.getBytes());
    System.out.println("in function here"+ new String(encrypted)+" and og: "+ a);

    return(new String(encrypted));    
    }
   

												/* 				AES    			*/


private static SecretKeySpec secretKey;
public static String aes(String a)throws Exception{
	String originalString = a;
	final String secretKey = "khddmdcm";
	String encryptedString = aesencrypt(originalString, secretKey);
	System.out.println(encryptedString);
	return(encryptedString);
}
public static String aesencrypt(String strToEncrypt, String secret)
{
	
    try
    {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }
    catch (Exception e)
    {
        System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
}
public static void setKey(String myKey)
{	
	byte[] key;
	SecretKeySpec secretKey;
    MessageDigest sha = null;
    try {
        key = myKey.getBytes(StandardCharsets.UTF_8);
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "AES");
    }
    catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
   /* catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }*/
}



												/* 				TDES   			*/


public static String tdes(String a) throws Exception {
	String inputText = a;
    String text = inputText;
    
    //long start = System.currentTimeMillis( );
    byte[] codedtext = tdesencrypt(text);
    //long end = System.currentTimeMillis( );
    //long diff = end - start;
    //System.out.println("Difference is : " + diff +"ms");

    
    
    //String decodedtext = new tdes().decrypt(codedtext);

    System.out.println(codedtext); // this is a byte array, you'll just see a reference to an array
    //System.out.println(decodedtext); // This correctly shows "kyle boon"
    return(new String(codedtext));
}
public static byte[] tdesencrypt(String message) throws Exception {
    final MessageDigest md = MessageDigest.getInstance("md5");
    final byte[] digestOfPassword = md.digest("HG58YZ3CR9".getBytes("utf-8"));
    final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
    for (int j = 0, k = 16; j < 8;) {
        keyBytes[k++] = keyBytes[j++];
    }

    final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);

    final byte[] plainTextBytes = message.getBytes("utf-8");
    final byte[] cipherText = cipher.doFinal(plainTextBytes);
    // final String encodedCipherText = new sun.misc.BASE64Encoder()
    // .encode(cipherText);

    return cipherText;
}
}

	
