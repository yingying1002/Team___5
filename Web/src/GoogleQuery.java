    import java.io.BufferedReader;
	import java.io.File;
	import java.io.IOException;
	
	import java.io.InputStream;
	
	import java.io.InputStreamReader;
    import java.net.CookieHandler;
    import java.net.CookieManager;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
	
	import java.net.URLConnection;
	import java.nio.charset.StandardCharsets;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Collections;
	import java.util.Comparator;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;
	import java.util.PriorityQueue;
	import java.util.Scanner;
	import java.util.Set;
	
	import org.jsoup.Jsoup;
	
	import org.jsoup.nodes.Document;
	
	import org.jsoup.nodes.Element;
	
	import org.jsoup.select.Elements;
	
	
	
	public class GoogleQuery 
	
	{
	
	 public String searchKeyword;
	
	 public String url;
	
	 public String content;
	 
	 //public int searchNum;
	 
	 public PriorityQueue<WebNode> heap;
	 
	 public String results;
	 
	 public KeywordList list;
	 
	 public HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
	 
	 public HashMap<Integer, String> scoreSet = new HashMap<Integer, String>();
	 
	 public HashMap<String, String> retVal = new HashMap<String, String>();
	 
	 HashMap<Integer, ArrayList<String>> resulting = new HashMap<Integer, ArrayList<String>>();
	
	 public GoogleQuery(String searchKeyword)
	
	 {
	
	  this.searchKeyword = searchKeyword;
	  //this.searchNum=searchNum;
	  setKeyword();
	  
	  list = new KeywordList();
	  
	  scoreMap = new HashMap<String, Integer>();
	
	  this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
	
	 }
	
	 public void setKeyword() {
	  
	 }
	 
	 public String fetchContent() throws IOException {
			String retVal = "";

			try {
				URL u = new URL(url);
				
				HttpURLConnection.setFollowRedirects(false);
				HttpURLConnection conn = (HttpURLConnection) u.openConnection();
				conn.setRequestProperty("Accept", "*/*");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0) Chrome/96.0.4664.110 Safari/15.2");
				CookieHandler.setDefault(new CookieManager());

				if (conn.getResponseCode() == 403 || conn.getResponseCode() == 400 || conn.getResponseCode() == 404) {
					retVal = url;
					System.out.printf("Error %d: %s\n", conn.getResponseCode(), retVal);
				} else {
					InputStream in = conn.getInputStream();
					retVal = new String(in.readAllBytes(), StandardCharsets.UTF_8); // will fail with large amounts of data
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				retVal = url;
			} catch (IOException e) {
				e.printStackTrace();
				retVal = url;
			}

			return retVal;
		}
	 public String fetchGoogle() throws IOException {
			String retVal = "";

			try {
				URL u = new URL(url);

				HttpURLConnection conn = (HttpURLConnection) u.openConnection();

				conn.setRequestProperty("User-agent", "Chrome/96.0.4664.110");
				 
				InputStream in = conn.getInputStream();
				InputStreamReader inReader = new InputStreamReader(in, "utf-8");

				BufferedReader bufReader = new BufferedReader(inReader);

				String line = null;
				while ((line = bufReader.readLine()) != null) {
					retVal += line;
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			return retVal;
		}
	 
	 
	
	 /*private String fetchContent() throws IOException
	
	 {
		  String retVal = "";
			
		  URL u = new URL(url);
		
		  //URLConnection conn = u.openConnection();
		  
		  HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		
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
	 }*/
	 
	 
	 public HashMap<String, String> query() throws IOException
	
	 {
		 ArrayList<String> error = new ArrayList<String>();
	
	  if(content==null)
	
	  {
	
	   content= fetchGoogle();
	
	  }
	  
	 
	
	  //HashMap<String, String> retVal = new HashMap<String, String>(); //改快趕回來
	  
	  Document doc = Jsoup.parse(content);
	//  System.out.println(doc.text());
	  Elements lis = doc.select("div");//把標題記起來
	//   System.out.println(lis);
	  lis = lis.select(".kCrYT");
	//   System.out.println(lis.size());
	  
	  
	  for(Element li : lis)
	  {
	   try 
	
	   {
	   // String citeUrl = li.select("a").get(0).attr("href");
	    //String title = li.select("a").get(0).select(".vvjwJb").text();
		   String citeUrl = li.select("a").attr("href"); // System.out.println("origin: " + citeUrl);
			if (citeUrl.startsWith("/url?q=")) {
				citeUrl = citeUrl.replace("/url?q=", "");
			}
			String[] splittedString = citeUrl.split("&sa=U");
			if (splittedString.length > 1) {
				citeUrl = splittedString[0];
			}
			
			// url decoding from UTF-8
			citeUrl = java.net.URLDecoder.decode(citeUrl, StandardCharsets.UTF_8);
			citeUrl.replaceAll(" ", "%20");

			// parse down title
			String title = li.select("a").select(".vvjwJb").text();
			if (title.equals("")) {
				continue;
			}
			
			if(content.equals(title)) {
				error.add(title);
				continue;
			}

			System.out.println(title + ", " + citeUrl);
			retVal.put(title, citeUrl);  //趕快改回來
	    //也許可以改放score跟citeUrl
	    
	    
	   // String newUrl = citeUrl.substring(7);
	    
	    WebPage rootPage = new WebPage(citeUrl, title);
	    ArrayList<Keyword> keywords = new ArrayList<Keyword>();
	    KeywordList lst = new KeywordList();
	    
	    
	    keywords.add(new Keyword("born",4));
	    keywords.add(new Keyword("died",5));
	    keywords.add(new Keyword("throne",5));
	    keywords.add(new Keyword("FinTech",5));
	   // keywords.add(new Keyword("杜甫",5));
	    //還沒使用file
	    
	    rootPage.setScore(keywords);
	    
	    int score = (int) rootPage.score;
	    
	    //list.add(new Keyword(title,score));
	    
	    
	
	    System.out.println(title+","+rootPage.score);
	    
	    scoreMap.put(title, score); //有差別的好像是這行
	  
	   //list.sort(); //要如何排沒有東西的值
	    
	    ArrayList<String> t = new ArrayList<String>();
	    t.add(title);
	    //t.add(citeUrl);
	    
	    
	    
	    resulting.put(Integer.valueOf(score), t); //應該是怪在這裏
	    
	 
	   } catch (IndexOutOfBoundsException e) {
	
	//    e.printStackTrace();
	
	   }
	   
	  }
	  for(String e : error) {
		  retVal.remove(e);
	  }
	
	  return retVal;
	
	 }
	 
	 public HashMap<String, String> getQuery(){
		 return retVal;
	 }
	 
	 @SuppressWarnings("unchecked")
	public HashMap<Integer, String> scoremap(){
		 
		 Map phone = scoreMap;
		 Set set = phone.keySet();
	     Object[] arr = set.toArray();
	     Arrays.sort(arr);
	     List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(phone.entrySet());
	     list.sort(new Comparator<Map.Entry<String, Integer>>() {
	         @Override
	         public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	             return o2.getValue().compareTo(o1.getValue());
	         }
	     });
	     //collections.sort()
	     Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	         @Override
	         public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	             return o2.getValue().compareTo(o1.getValue());
	         }
	     });
	     //for
	     for (int i = 0; i < list.size(); i++) {
	    	 scoreSet.put(list.get(i).getValue(), list.get(i).getKey());
	    	 //scoreSet.put(list.get(i).getKey(), list.get(i).getValue()); //這樣就有放進一個hashMap還有排好
	         System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
	     }
	     System.out.println();
	     /*for-each
	     for (Map.Entry<String, Integer> mapping : list) {
	         System.out.println(mapping.getKey() + ": " + mapping.getValue());
	     }
	     */
		 return scoreSet;
	 }
	 
	 public HashMap<Integer, ArrayList<String>> getResulting(){
		 return resulting;
	 }

	 public Map<Integer,String> getscoremap() {
		 return scoreSet;
	 }

	public List<Integer> sort(HashMap<Integer, String> score) {
		// TODO Auto-generated method stub
		
         List<Integer> r = new ArrayList<Integer>();
		 
		 for(Integer s : score.keySet()) {
			 r.add(s);
		 }
		 Collections.sort(r,Collections.reverseOrder());
		 return r;
	}
	  
	 /*public  HashMap<String,String> hashmap() {
		 //scoreSet retVal
		 HashMap<String,String> results = new HashMap<String,String>();
			
		 List<Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(scoremap().entrySet());
		 List<Entry<String, String>> list2 = new ArrayList<Map.Entry<String,String>>(query().entrySet());
		 
		 for(Entry<String, Integer> mapping:list){//title,score
				for(Entry<String, String> result:list2) {//title,citeUrl
					if(mapping.getKey().equals(result.getKey())) {
						String key = result.getKey();
						String value = result.getValue();
						results.put(key,value);
					}
				}
		 return results;
	 }*/
	
	 
	
	}