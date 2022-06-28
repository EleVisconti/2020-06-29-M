package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void listAllDirectors(Map <Integer, Director> idMap){
		String sql = "SELECT * FROM directors";

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				idMap.put(res.getInt("id"),director);
			}
			conn.close();
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public List<Integer> getVertex(Map <Integer, Director> idMap, int anno){
		String sql = "SELECT DISTINCT md.director_id as m1 "
				+ "FROM movies m, movies_directors md "
				+ "WHERE m.year=? AND m.id=md.movie_id "
				+ "ORDER BY md.director_id";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try { 
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {		
				result.add(res.getInt("m1"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(int anno){
			String sql = "SELECT md1.director_id, md2.director_id, COUNT(DISTINCT r1.actor_id) AS peso, md1.movie_id "
					+ "FROM roles r1, movies_directors md1, roles r2, movies_directors md2, movies m1, movies m2 "
					+ "WHERE md1.director_id<md2.director_id AND r1.movie_id=md1.movie_id "
					+ "AND r2.movie_id=md2.movie_id "
					+ "AND r1.actor_id=r2.actor_id "
					+ "AND m2.id=md2.movie_id AND m1.id=md1.movie_id "
					+ "and m2.year=? AND m1.year=? "
					+ "GROUP BY md1.director_id, md2.director_id";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {		
				Adiacenza a = new Adiacenza(res.getInt("md1.director_id"),res.getInt("md2.director_id"), res.getInt("peso"));
				 result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	
	
}
