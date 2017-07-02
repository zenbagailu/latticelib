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
import java.util.*;


/**
* This is the base abstract class from where all lattice cells inherit. It includes a basic constructor,
* the genNeig() method, together with the essential abstract methods init() and step(), and an empty draw() 
* method, that can be either over-ridden or left as it is, if there is no need for one. It also includes 
* the essential fields of the cell, a position and its buffer. To allow the free implementation of 
* constructors the initialise() method is used instead to set up these fields. If it is necessary to access these
* at some point during initialisation, this can be done in the abstract method init(), which is called
* after initialise() by the Lattice class.
* LatticeCell implements the Curiously Recurring Template Pattern in order to traverse a collection (in this case 
* an object of type Buffer) of its super-classes, and thus access their specific methods and fields.
* The same design pattern is applied in other places in the lattice package.
* 
* @author Pablo Miranda Carranza
*/

public abstract class LatticeCell <T extends LatticeCell<T> >{

	protected Buffer<T> buffer; 
	protected Vector pos;


	public LatticeCell(){}

    /**
     * Package private method called by lattice.Lattice, after constructor, but before calling the init() method
     * @param  buffer       Buffer{@literal <}CT{@literal >}) the LatticeCell belongs to.
     * @param  pos          The position (and dimensions) of the  LatticeCell.
     */
	void initialise(Buffer<T> buffer,Vector pos){ //package-private. used by lattices 

		this.buffer=buffer;
		this.pos=pos;
	}

    /**
     * @param  np       Vector describing the position of the neighbour relative to this LatticeCell. 
     * If it is outside the buffer, the position will be wrapped.
     * @return the LatticeCell corresponding to the relative position given in np
     */
	protected T getNeig(Vector np){
		return buffer.get(pos.addition(np));
	}

    /**
     * Abstract method called for initialisation. All initialisation should generally be done in  the implementation 
     * of this method.
     */
	public abstract void init();
    /**
     * Abstract method in which the calculations will take place. It should take care of updating and copying all fields
     * (from oldCell) that may be changed in the execution of the lattice. 
     * @param  oldCell       The past states and fields of this cell.
     */
    
	public abstract void step(T oldCell);

    /**
     * an empty method that can be either over-ridden if the cell is going to do any drawing 
     * (it is called from the drawing() method in the Lattice class).
     */
	public void draw(){}; //this way it is optional...
}