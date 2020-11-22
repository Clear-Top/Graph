package dbp.test;

import dbp.project.graph.Edge;
import dbp.project.graph.Graph;
import dbp.project.graph.Vertex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//null point에 대해 처리할것인지? 그런 상황은 없다고 가정하는지 궁금합니당

public class JGraph implements Graph {
    
	// 쿼리문 사용하기 위해 가져옴
    private Statement m_stmt = null;
    
    public void setStatement(Statement stmt) { m_stmt = stmt; }
    
	public JGraph() {
		super();
	}

	@Override
    public Vertex addVertex(String id) {
		int intID = Integer.parseInt(id);
		Vertex v = null;
				
        try {
        	m_stmt.executeUpdate("INSERT INTO vertices (ID) VALUE(" + intID + ");");
          	//!!!!!vertex 변수 하나 선언해서 거기에 추가하고 return하는것인지..?-> 디자인 추후 논의!!!!!
          	//!!!!!vertex class에 v.setID 메소드가 없는게 맞나요? (아래 모두 마찬가지)
          	return v;
        } catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public Vertex getVertex(String id) {
    	ResultSet rs;
    	int intID = Integer.parseInt(id);
    	Vertex v = null;
    	
		try {
			rs = m_stmt.executeQuery("SELECT * FROM vertices WHERE ID=" + intID);
			//확인용으로  출력했던 코드
			/*  while(rs.next()) {
		    	Object vetID = rs.getObject(1);
				System.out.println(vetID);
			}*/
			
			//v 세팅 후 반환
			return v;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    @Override
    public Iterable<Vertex> getVertices() {
    	//vertex 리스트를 생성하여 그 리스트를 가리키는 이터레이터 반환..?
    	//!!!!!리스트를 메모리에 유지하지 말라 하셨는데 그 부분 논의
    	List<Vertex> vertexData = new ArrayList<Vertex>();
    	Iterator<Vertex> iter;
    	ResultSet rs;
    	Vertex v = null;
   
    	try {
    		rs = m_stmt.executeQuery("SELECT * FROM vertices;");
			while(rs.next()) {
				//v 세팅 후 추가?
				//v.setID(rs.getObject(1)); v.setProperty(rs.getObject(2));
				vertexData.add(v);
				//System.out.print(rs.getObject(1)); //확인용 코드
			}
			iter = vertexData.iterator();
			
			return (Iterable<Vertex>) iter;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        return null;
    }

    @Override
    public Iterable<Vertex> getVertices(String key, Object value) {
        return null;
    }

    @Override
    public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
    	// insert into edges value ();
    	Object outID = outVertex.getId();
    	Object inID = inVertex.getId();
    	Edge edge = null;
    	
    	try {
    		m_stmt.executeUpdate("INSERT INTO edges (OutV, InV, Label) "
					+ "VALUE(" + outID + inID + label + ");");
			//e.setID 해서 반환
			return edge;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        return null;
    }

    @Override
    public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
    	ResultSet rs;
    	Object outID = outVertex.getId();
    	Object inID = inVertex.getId();
    	Edge edge = null;
    	
		try {
			rs = m_stmt.executeQuery("SELECT * FROM edges "
					+ "WHERE OutV=" + outID + " and " + "InV=" + inID + " and " + "label=" + label);
			
			//edge 세팅 후 반환 -> !!!!!vertex와 같은 이슈 존재
			//edge.setID();
			return edge;
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }

    @Override
    // 그래프내의 모든 엣지를 반환하는 메소드
    public Iterable<Edge> getEdges() {
    	// List 컬렉션에 모든 엣지들을 추가해서 컬렉션자체를 리턴 (메인에서 iterator 생성)
    	List<Edge> edgeData = new ArrayList<Edge>();
    	ResultSet rs;
    	
    	try {
			rs = m_stmt.executeQuery("SELECT * FROM edges;");
			while(rs.next()) {
				// JEdge객체를 생성하고, 고유ID를 setting 후에 컬렉션에 붙이기
				// 질문 : property 처리는 어떻게 해야하나요..
				JEdge e = new JEdge();
				e.setID(rs.getString(1), rs.getString(2), rs.getString(3));
				edgeData.add(e);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        return null;
    }

    @Override
    public Iterable<Edge> getEdges(String key, Object value) {
    	List<Edge> edgeData = new ArrayList<Edge>();
    	edgeData=null;
    	try {
    		// 제이슨 라이브러리 사용가능?
			ResultSet rs = m_stmt.executeQuery("select json_value(properties,'$."+key+"') from edges;");
			while(rs.next()) {
				String str = rs.getString(1);
				if(value.equals(str)) {
					JEdge e = new JEdge();
					//edge setting?
					edgeData.add(e);
				}
			}
			return edgeData;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}