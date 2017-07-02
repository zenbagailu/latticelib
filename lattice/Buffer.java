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
 * lattice package, library to make easier writing Cellular Automata in Processing.
 *
 * @author Pablo Miranda Carranza
 */


package lattice;

import java.util.ArrayList;
import java.util.Iterator;


/**
* Buffer class used for buffers of cells.
* 
* @author Pablo Miranda Carranza
*/

public class Buffer<CT extends LatticeCell<CT>> implements Iterable<CT> {
	ArrayList<CT> cells; 
	Vector sizeVec;

    /**
     * The constructor.
     * @param sizeVec   Vector that describes the sizes (in the number of  dimensions of  the specific implementation of Vector) of the buffer. 
    **/

	Buffer(Vector sizeVec){
		this.sizeVec=sizeVec;
		cells = new ArrayList<CT>(sizeVec.coverSize());
	}

	void add(CT cell){
		cells.add(cell);
	}

	CT get(Vector vec){
		return cells.get(vec.wrapAround(sizeVec).unfoldOn(sizeVec));
	}

    /**
     * Implementation of the iterator() method, as part of Iterable{@literal <}CT{@literal >} 
     * @return      An iterator (Iterator{@literal <}CT{@literal >}) over all cells in the Buffer.
    **/

	public Iterator<CT> iterator(){	 //allow iteration through all elements as they are ordered
		return cells.iterator();
	}

    /**
     * It instantiates an anonymous Iterable{@literal <}CT{@literal >} class that returns an 
     * Iterator{@literal <}CT{@literal >} over the neighbourhood defined by neighbours.
     * @param pos           describes the position of the cell.
     * @param  neighbours   the positions, in their adequate dimension, of the neighbours. 
     * @return An Iterable{@literal <}CT{@literal >}  object that returns an Iterator{@literal <}CT{@literal >} over the neighbourhood defined by neighbours.
    **/

	Iterable<CT> getIterableInstance(Vector pos, Vector[] neighbours){
		NeighborhoodIterator<CT> it=new NeighborhoodIterator<CT>(this,pos, neighbours);
		return new Iterable<CT> () {
			public Iterator<CT> iterator(){	
				return it;
			}
		};
	}

    
}
