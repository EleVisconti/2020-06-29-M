package it.polito.tdp.imdb.model;

import java.util.Objects;

public class Adiacenza {
int d1;
int d2;
int peso;
public int getD1() {
	return d1;
}
public void setD1(int d1) {
	this.d1 = d1;
}
public int getD2() {
	return d2;
}
public void setD2(int d2) {
	this.d2 = d2;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}
public Adiacenza(int d1, int d2, int peso) {
	super();
	this.d1 = d1;
	this.d2 = d2;
	this.peso = peso;
}
@Override
public int hashCode() {
	return Objects.hash(d1, d2);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Adiacenza other = (Adiacenza) obj;
	return d1 == other.d1 && d2 == other.d2;
}


 
}
