import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;



/**
 * 
 * @author penglong
 *
 */
public class JBCTest 
{
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException 
	{
		InputStream in = new FileInputStream("D:\\eclipse_for_rhzx_workspace\\intelligent-crawler-common\\target\\classes\\intelli\\crawler\\dao\\util\\SqlBuilder.class");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		
		String newLine = null;
		try {
			while ( (newLine = br.readLine()) != null )
			{
				System.out.println(newLine );
			}
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}finally
		{
			try {
				br.close();
				in.close();
			} catch (IOException e) 
			{
				br=null;
				in=null;
				
			}
			
		}
		
		
	}
}
