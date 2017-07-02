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

import java.util.ArrayList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;

import java.lang.Iterable;

/**
 * The Lattice Class implements the basic mechanisms (through double buffering) for maintaining 
 * the current and previous states of the cells. Because it instantiates also the cells in both buffers, 
 * it uses reflection in order access the actual constructors of the classes given in their generic form (and 
 * of which we don't know their final type in Java). The Lattice class is also responsible of iterating through the cells
 * calling their init(), step(), and draw() methods, and for swapping the buffers of past and present cells.
 * Because the intend of this whole library is to be used within Processing, the cell classes (the CT type in the generic
 * definition) will be implemented as inner classes to a class extending PApplet. Thus the instance of that PApplet class to 
 * which the CT objects will be internal to is necessary in order to instantiate the CT objects using reflection, and need to 
 * be passed in the constructor. The constructor also needs the class type of CT (because of Java type erasure this is not 
 * available to CT), which is also needed in the constructor.
 * 
 * @author Pablo Miranda Carranza
 */

public class Lattice<CT extends LatticeCell<CT>> {

	Buffer<CT> pastCells;
	Buffer<CT> presentCells;

    
    /**
     * Constructor for 2D Lattices. 
     * @param pt            A reference to the PApplet object in which the  CT class is implemented. 
     * @param cellType      The Class of the CT parameter.
     * @param w             Width of the lattice.
     * @param h             Height of the lattice.
     */
	public <PT> Lattice (PT pt, Class<CT> cellType, int w, int h) {
        this(pt,cellType,new Vector2D(w,h));
	}

    /**
     * Constructor for 3D Lattices. 
     * @param pt            A reference to the PApplet object in which the  CT class is implemented. 
     * @param cellType      The Class of the CT parameter.
     * @param w             Width of the lattice.
     * @param h             Height of the lattice.
     * @param d             Depth of the lattice.
     */
    public <PT> Lattice (PT pt, Class<CT> cellType, int w, int h, int d) {
        this(pt,cellType,new Vector3D(w,h,d));
    }

    /**
     * General constructor for lattices.
     * @param pt            A reference to the PApplet object in which the  CT class is implemented. 
     * @param cellType      The Class of the CT parameter.
     * @param size          A Vector implementation with the correct methods for calculating buffer sizes and positions
     */
    public <PT> Lattice (PT pt, Class<CT> cellType, Vector size) {
       
        pastCells = new Buffer<CT>(size);
        presentCells = new Buffer<CT>(size);

        for(int i=0;i<size.coverSize(); ++i){
            Vector pos=size.foldInto(i);

            CT pastCell=createCellInstance(pt,cellType);
            pastCell.initialise(pastCells, pos); 
            pastCells.add(pastCell);

            CT presentCell=createCellInstance(pt,cellType);
            presentCell.initialise(presentCells, pos); 
            presentCells.add(presentCell);

        }

        //now call the init methods, now that all cells are created, their positions and buffers assigned...
        for(CT cell: pastCells){
            cell.init();
        }
        
        for(CT cell: presentCells){
            cell.init();
        }
    }



    /**
     * @param  pt            A reference to the PApplet object in which the  CT class is implemented. 
     * @param  cellType      The Class of the CT parameter.
     * @return A new instance of a Cell (of the same type as defined for CT, given in cellType parameter) 
     */
	private <PT> CT createCellInstance (PT pt, Class<CT> cellType){
		try {

		    Class<?> processingOuterType=pt.getClass();
		    Constructor<CT> constructor = cellType.getDeclaredConstructor(new Class<?>[]{processingOuterType});
		    constructor.setAccessible(true);
            try {

	            CT cell=constructor.newInstance(new Object[] {pt});
			 	return cell;
	        }
	        catch (InstantiationException ie) {
	            //handle InstantiationException
	            System.out.println("Instantiation exception: ");
	            System.out.println(ie.getMessage());
	        }
	        catch (IllegalAccessException iae) {
	            //handle IllegalAccessException
	            System.out.println("Illegal access exception: ");
	            System.out.println(iae.getMessage());
	        }
	        catch (InvocationTargetException ite) {
	            //handle InvocationTargetException
	            System.out.println("Invocation target exception: ");
            	System.out.println(ite.getMessage());
	        }

	    }
        catch (NoSuchMethodException nsme) {
            //handle constructor not being found
            System.out.println("Constructor not found.");
            System.out.println(nsme.getMessage());
        }
        // catch (NoSuchFieldException nsfe) {
        //     //handle constructor not being found
        //     System.out.println("filed does not exist.");
        //     System.out.println(nsfe.getMessage());
        // }

        return null;

	}
	
    /**
     * @return the pastCells buffer , which implements Iterable{@literal <}CT{@literal >} 
     */
	public Iterable<CT> allPastCells(){
		return pastCells; //ArrayList implements Iterable
	}

    /**
     * @return the presentCells buffer , which implements Iterable{@literal <}CT{@literal >} 
     */
	public Iterable<CT> allPresentCells(){
		return presentCells; //ArrayList implements Iterable
	}

    /**
     * It calls the draw() method for all past cells
     */
	public void draw(){
		for(CT cell:pastCells){
			cell.draw();
		}
	}

    /**
     * It calls the step() method for all present cells, and swaps the present and past cells when done.
     */
	public void step(){
		for(CT cell:presentCells){
			cell.step(pastCells.get(cell.pos));
		}

		//and swap:
		Buffer<CT> temp=pastCells; 
		pastCells=presentCells;
		presentCells=temp;
	}

}