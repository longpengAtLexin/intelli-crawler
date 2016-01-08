import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class RegexTest 
{
	public static void main(String[] args) 
	{
		test2();
		//test1();
	}
	
	
	public static  void test1()
	{
		String abc = "https://www.mo9.com/creditCenter/p/1";
		
		String patternStr = "https://www.mo9.com/creditCenter/p/[0-9]+";
		
		Pattern p = Pattern.compile(patternStr);
		
		Matcher matcher = p.matcher(abc);     
		
		//String result = null;
		if(matcher.find())
		{
			int index = matcher.end() ;  // 最后一个匹配分页模式(pattern ) 的字符;
			System.out.println( index );
			System.out.println( matcher.regionStart() );
			
			System.out.println( matcher.start());
			
			System.out.println( "哈哈" );
			//String result = matcher.replaceFirst("https://www.mo9.com/creditCenter/p/2");
			
			String result2 = abc.substring(0,index - Integer.toString(1).length());
			
			System.out.println( result2 );
			
			String postStr = abc.substring(index-1);
			
			System.out.println("后半部分： "   +postStr);
			
		}
			
 
		
		//System.out.println( result );
	}
	
	public static  void test2()
	{
		String seedurl1 = "http://sz.58.com/ershouche/pn2/?minprice=8_10&utm_source=market&spm=b-31580022738699-me-f-824.bdpz_biaoti&PGTID=174577279189690766449314300&ClickID=1";
		
		String pagingUrlPattern =  "http://sz.58.com/ershouche/pn[0-9]+";
		
		Pattern p = Pattern.compile(pagingUrlPattern);
		
		Matcher matcher = p.matcher(seedurl1);     
		
		if(matcher.find())
		{
			int index = matcher.end();
			
			System.out.println( "哈哈" +index );
			
		}
		
		
	}
	
}
