/*
Copyright 2017 Pablo Miranda Carranza

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

/**
 *  lattice package, library to make easier writing Cellular Automata in Processing.
 *
 *  @author Pablo Miranda Carranza
 */

package lattice;

import java.util.Iterator;

/**
* Iterator that allows to access the neighbours of a cell. Given an array with the coordinates for each 
* neighbour defining a neighbourhood of a cell, it iterates through all of them. This allows to use simple 
* for-each or enhanced for statements to iterate through the neighbours.
* @author Pablo Miranda Carranza
*/

public class NeighborhoodIterator<CT extends LatticeCell<CT> > implements Iterator<CT>{

	int current;
	Buffer<CT> buffer;
	Vector pos;
	final Vector[] neighbours;
    /**
     * @param buffer
     * @param pos              the coordinates of the LatticeCell for which the neighbourhood is defined
     * @param neighbours       an array of neighbour coordinates in the form of implementations of lattice.Vector 
     */
	public NeighborhoodIterator (Buffer<CT> buffer, Vector pos, Vector[] neighbours){
		current=0;
		this.buffer=buffer;
		this.pos=pos;
		this.neighbours=neighbours;	
	}
    /**
     * Implementation of next() method from java.util.Iterator.
     * @return a LatticeCell
     */
	public CT next() {
		int prev=current++;
		return buffer.get(pos.addition(neighbours[prev]));
	}

    /**
     * Implementation of hasNext() method from java.util.Iterator.
     */
	public boolean  hasNext() {
		return current<neighbours.length;
	}
    /**
     * empty implementation of remove() method from java.util.Iterator. Cells should not be removed from the lattices, 
     * because then the order of the elements would be invalidated (the order in the buffer and the positions described
     * by lattice.Vector need to correspond).
     */
	public void remove(){
		System.out.println("the remove() method in NeighborhoodIterator does nothing...");
 	}
    
}

