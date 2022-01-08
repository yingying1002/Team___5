
/*import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URL;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;



public class SearchQuery

{

 public String searchKeyword;

 public String url;

 public String content;
 
 //public int searchNum;
 
 public PriorityQueue<WebNode> heap;
 
 public String results;

 public GoogleQuery(String searchKeyword)

 {

  this.searchKeyword = searchKeyword;
  //this.searchNum=searchNum;
  setKeyword();

  this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";

 }

 public void setKeyword() {
  
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
//  System.out.println(doc.text());
  Elements lis = doc.select("div");//把標題記起來
//   System.out.println(lis);
  lis = lis.select(".kCrYT");
//   System.out.println(lis.size());
  
  
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
    
    WebPage rootPage = new WebPage(citeUrl, title);
    WebNode a = new WebNode(rootPage);
    KeywordList lst = new KeywordList();
    File file = new File("input.txt");  
    Scanner scanner = new Scanner(file);
   
    while(scanner.hasNextLine()){
     String operation = scanner.next();
     
     switch (operation){
      case "add":
       String name = scanner.next();
       int count = scanner.nextInt();   
       lst.add(new Keyword(name, count));
       break;
      default:
       System.out.println("InvalidOperation");
       break;
     } 
    }
    scanner.close();
    //ArrayList<Keyword> keywords = new ArrayList<Keyword>();
    //keywords.addAll(lst);
    //rootPage.setScore(lst.lst);//放arraylist<keyword> 
    System.out.println(title+","+a.nodeScore);//分數為何跑不出來

   } catch (IndexOutOfBoundsException e) {

//    e.printStackTrace();

   }

   

  }

  

  return retVal;

 }

 

 

}*/
