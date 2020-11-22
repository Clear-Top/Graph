package kr.ac.sejong.dbp.dbp_project;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
    	TinkerGraph g = new TinkerGraph();
    	// vertex 0 추가
    	Vertex v0 = g.addVertex("0");
    	v0.setProperty("name", "jack");
    	v0.setProperty("age", 21);
    	v0.setProperty("age", 23);
    	v0.setProperty("bloodType", 'B');
    	
    	Vertex v00 = g.getVertex("0");
    	System.out.println(v0);
    	Object name = v00.getProperty("name");
//    	Object name = v00.getProperty("name");    	
//    	Object name = v00.getProperty("name");    	
//    	Object name = v00.getProperty("name");    	
//    	Object name = v00.getProperty("name");
//    	Object name = v00.getProperty("name");
//    	Object name = v00.getProperty("name");
    }
}
