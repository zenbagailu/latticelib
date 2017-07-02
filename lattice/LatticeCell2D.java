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

/**
* Extension of the LatticeCell class for 2D, with definitions for most commons 2D neighbourhoods. 
* These include the VonNeumann and Moore neighbourhoods, as well as a neighbourhood consisting of 
* the cells at the corners of the current cell. These neighbourhoods are described using the simple 
* convention of enumerate the neighbours from left to right, and top to bottom, that may allow to 
* pair them with values of for example convolution filters and other data specific to each neighbour.
* The diagonalNeigs() and vonNeumannNeigs() methods, however, allow to separate the cells in their
*  most typical categories. 
* 
* @author Pablo Miranda Carranza
*/

public abstract class LatticeCell2D <T extends LatticeCell2D<T>> extends LatticeCell <T>{
	//The neighbours are given left to right, top to bottom

    static private Vector2D[] DiagonalNeighbours={
        new Vector2D(-1,-1),                            new Vector2D( 1,-1),
                                                        
        new Vector2D(-1, 1),                            new Vector2D( 1, 1)
    };

    static private Vector2D[] VonNeummmanNeighbours={
                                    new Vector2D( 0,-1), 
         new Vector2D( -1, 0),                              new Vector2D( 1, 0), 
                                    new Vector2D( 0, 1)
    };

    static private Vector2D[] MooreNeighbours={
        new Vector2D(-1,-1),    new Vector2D( 0,-1),    new Vector2D( 1,-1),
        new Vector2D(-1, 0),                            new Vector2D( 1, 0),
        new Vector2D(-1, 1),    new Vector2D(0, 1),     new Vector2D( 1, 1)
    };

    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the diagonal neighbours (those touching at the corners)
     */
    protected Iterable<T> diagonalNeigs(){ 
        return buffer.getIterableInstance(pos,MooreNeighbours);
    }
    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the vonNeumann neighbourhood (neighbours sharing faces).
     */
	protected Iterable<T> vonNeumannNeigs(){
		return buffer.getIterableInstance(pos,VonNeummmanNeighbours);
	}
    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the Moore neighbourhood (all neighbours touching the LAtticeCell2D).
     */
	protected Iterable<T> mooreNeigs(){	
		return buffer.getIterableInstance(pos,MooreNeighbours);
	}

    /**
     * Get a neighbour with the given relative position to this cell.
     * If the position of the neighbour is outside the buffer, the position will be wrapped.
     * @param  offx         The offset or relative x position relative to this LAtticeCell2D
     * @param  offy         The offset or relative y position relative to this LAtticeCell2D
     * @return the LatticeCell corresponding to the relative position of offx and offy.
     */
	protected T getNeig(int offx, int offy){
		return getNeig(new Vector2D(offx,offy));
	}


	public int getX(){return pos.get(0);}

	public int getY(){return pos.get(1);}
}

