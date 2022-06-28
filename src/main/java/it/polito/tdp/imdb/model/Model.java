package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	ImdbDAO dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private List<Integer> vertici;
	private List<Adiacenza> archi;
	

	public Model() {
		this.dao = new ImdbDAO();
		idMap = new HashMap<Integer, Director>();
		this.dao.listAllDirectors(idMap);
	}
	

	public void creaGrafo(int anno) {
     this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
     vertici = new ArrayList<Integer>(this.dao.getVertex(idMap, anno));
     Graphs.addAllVertices(this.grafo, this.vertici);
     archi = new ArrayList<Adiacenza>();
     for(Adiacenza a : this.dao.getArchi(anno)) {
    	 if(vertici.contains(a.getD1())&&vertici.contains(a.getD2())) {
    		 Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(), a.getPeso());
    		 archi.add(a);
    	 }
     }}
	
     public List<Integer> getVertici() {
		return vertici;
	}


	public void setVertici(List<Integer> vertici) {
		this.vertici = vertici;
	}


	public List<Adiacenza> getArchi() {
		return archi;
	}


	public void setArchi(List<Adiacenza> archi) {
		this.archi = archi;
	}


	public int nVertici() {
 		return this.grafo.vertexSet().size();
 	}
 	
 	public int nArchi() {
 		return this.grafo.edgeSet().size();
 	}


	public String getAdiacenti(Integer regista) {
		String s = "";
		for(Integer r : Graphs.neighborListOf(this.grafo, regista)) {
			Director r1 = idMap.get(r);
			DefaultWeightedEdge e = this.grafo.getEdge(regista, r);
			if(e==null)
				e = this.grafo.getEdge(r, regista);
			
			s+="\n"+r1.getFirstName()+" "+r1.getLastName()+" "+r1.getId()+" "+this.grafo.getEdgeWeight(e);
		}
		return s;	
			
		
	}
 	
	

}
