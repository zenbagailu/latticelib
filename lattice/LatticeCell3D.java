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
* Extension of the LatticeCell class for 3D, including definitions of the most common neighbourhoods. 
* The neighbourhoods are described using a simple convention allows to pair them with values of for 
* example convolution filters and other data that may be specific to each neighbour. This convention 
* is simply to enumerate the neighbours from left to right, top to bottom.
* The neighbourhoods implemented in LatticeCell3D allow, however, to separate the cells in their most typical
* categories (touching the central cell only on the corner, sharing an edge or a face, for example).
* 
* @author Pablo Miranda Carranza
*/

public abstract class LatticeCell3D <T extends LatticeCell3D<T>> extends LatticeCell <T>{
  

    static private Vector3D[] MooreNeighbours={

        //top
        new Vector3D(-1,-1,-1), new Vector3D( 0,-1,-1), new Vector3D( 1,-1,-1),
        new Vector3D(-1, 0,-1), new Vector3D( 0, 0,-1), new Vector3D( 1, 0,-1), 
        new Vector3D(-1, 1,-1), new Vector3D( 0, 1,-1), new Vector3D( 1, 1,-1),

        //middle
        new Vector3D(-1,-1, 0), new Vector3D( 0,-1, 0), new Vector3D( 1,-1, 0),
        new Vector3D(-1, 0, 0),                         new Vector3D( 1, 0, 0), 
        new Vector3D(-1, 1, 0), new Vector3D( 0, 1, 0), new Vector3D( 1, 1, 0),

        //bottom
        new Vector3D(-1,-1, 1), new Vector3D( 0,-1, 1), new Vector3D( 1,-1, 1),
        new Vector3D(-1, 0, 1), new Vector3D( 0, 0, 1), new Vector3D( 1, 0, 1), 
        new Vector3D(-1, 1, 1), new Vector3D( 0, 1, 1), new Vector3D( 1, 1, 1)

    };

    static private Vector3D[] VonNeumannNeighbours={

        //top
                                                      
                                new Vector3D( 0, 0,-1), 
        
        //middle
                                new Vector3D( 0,-1, 0),
        new Vector3D(-1, 0, 0),                         new Vector3D( 1, 0, 0), 
                                new Vector3D( 0, 1, 0),

        //bottom
        
                                new Vector3D( 0, 0, 1)

    };

    static private Vector3D[] EdgeNeighbours={

        //top
                                new Vector3D( 0,-1,-1), 
        new Vector3D(-1, 0,-1),                         new Vector3D( 1, 0,-1), 
                                new Vector3D( 0, 1,-1), 

        //middle
        new Vector3D(-1,-1, 0),                         new Vector3D( 1,-1, 0),
                                 
        new Vector3D(-1, 1, 0),                         new Vector3D( 1, 1, 0),

        //bottom
                                new Vector3D( 0,-1, 1),
        new Vector3D(-1, 0, 1),                         new Vector3D( 1, 0, 1), 
                                new Vector3D( 0, 1, 1)

    };

    static private Vector3D[] CornerNeighbours={
        //top
        new Vector3D(-1,-1,-1),                         new Vector3D( 1,-1,-1),
                                
        new Vector3D(-1, 1,-1),                         new Vector3D( 1, 1,-1),

        //middle
        
        
        

        //bottom
        new Vector3D(-1,-1, 1),                         new Vector3D( 1,-1, 1),
        
        new Vector3D(-1, 1, 1),                         new Vector3D( 1, 1, 1)

    };

    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the 26 Moore neighbours (in 3D)
     */
    protected Iterable<T> mooreNeigs(){
        return buffer.getIterableInstance(pos,MooreNeighbours);
    }

    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the vonNumann cells sharing faces with this LatticeCell3D
     */
    protected Iterable<T> vonNeumannNeigs(){
        return buffer.getIterableInstance(pos,VonNeumannNeighbours);
    }

    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the 12 cells sharing edges with this LatticeCell3D
     */
    protected Iterable<T> edgeNeigs(){
        return buffer.getIterableInstance(pos,EdgeNeighbours);
    }

    /**
     * @return an Iterable{@literal <}CT{@literal >} object over the 8 cells sharing vertices with this LatticeCell3D
     */
    protected Iterable<T> cornerNeigs(){
        return buffer.getIterableInstance(pos,CornerNeighbours);
    }

    /**
     * Get a neighbour with the given relative position to this cell.
     * If the position of the neighbour is outside the buffer, the position will be wrapped.
     * @param  offx         The offset or relative x position relative to this LAtticeCell3D
     * @param  offy         The offset or relative y position relative to this LAtticeCell3D
     * @param  offz         The offset or relative z position relative to this LAtticeCell3D
     * @return the LatticeCell corresponding to the relative position of offx, offy and offz.
     */

    protected T getNeig(int offx, int offy, int offz){
        return getNeig(new Vector3D(offx,offy,offz));
    }

    public int getX(){return pos.get(0);}
    public int getY(){return pos.get(1);}
    public int getZ(){return pos.get(2);}
}

