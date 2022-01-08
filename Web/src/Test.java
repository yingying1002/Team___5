/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;



public class Test {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//root node
		WebPage rootPage = new WebPage("http://soslab.nccu.edu.tw/Welcome.html", "Soslab");		
		WebTree tree = new WebTree(rootPage);
		//build childnode
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Publications.html","Publication")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Projects.html","Projects")));
		tree.root.children.get(1).addChild(new WebNode(new WebPage("https://vlab.cs.ucsb.edu/stranger/", "Stranger")));
		tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Members.html", "MEMBER")));
		tree.root.addChild(new WebNode(new WebPage("http://www3.nccu.edu.tw/~yuf/course.htm","Course")));
		//read 2 Yu 1.2 Fang 1.8 
		Scanner scanner = new Scanner(System.in);
		
		while(scanner.hasNextLine()){
			int numOfKeywords = scanner.nextInt();//2
			ArrayList<Keyword> keywords = new ArrayList<Keyword>();
			
			for(int i =0;i<numOfKeywords;i++)
			{
				String name = scanner.next();//Yu
				double weight = scanner.nextDouble();//1.2
				Keyword k = new Keyword(name, weight);//store key
				keywords.add(k);
			}
			
			tree.setPostOrderScore(keywords);
			tree.eularPrintTree();
		}
		scanner.close();
	}
	
	static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() 
        {
            public boolean verify(String hostname,SSLSession session) 
            {
                return true;
            }
        });
    }
	
	public String searchKeyword;

	public String url;

	public String content;

	public GoogleQuery(String searchKeyword)

	{

		this.searchKeyword = searchKeyword;

		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";

	}

	

	private String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in,"utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line=bufReader.readLine())!=null)
		{
			retVal += line;

		}
		return retVal;
	}
	public HashMap<String, String> query() throws IOException

	{

		if(content==null)

		{

			content= fetchContent();

		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		Document doc = Jsoup.parse(content);
//		System.out.println(doc.text());
		Elements lis = doc.select("div");
//		 System.out.println(lis);
		lis = lis.select(".kCrYT");
//		 System.out.println(lis.size());
		
		
		for(Element li : lis)
		{
			try 

			{
				String citeUrl = li.select("a").get(0).attr("href");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if(title.equals("")) {
					continue;
				}
				System.out.println(title + ","+citeUrl);
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {

//				e.printStackTrace();

			}

			

		}

		

		return retVal;

	}

	

	


	


}*/
