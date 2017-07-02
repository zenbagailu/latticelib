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

/**
* This class provides an interface for dealing with 2D, 3D and eventually other dimensional lattices using a strategy pattern.
* It implements typical lattice operations, such as getting a size or position in a 1D buffer from 2D 
* or 3D coordinates, calculate the size of 1D buffers from width, height or depth, and wrapping lattice
* cell indices into "toroidal" arrangements. The intention of the class is to allow to abstract all 
* dimension related operations from the buffers and other data structures, so they can be easily 
* adapted to different dimensions.
* *  @author Pablo Miranda Carranza
*/

public abstract class Vector {
	
    /**
     * @return the size of an array capable of holding a * b * c * ...n elements (a,b,c...n being the components of the vector). 
     */
	abstract int coverSize();
    /**
     * @param   a Vector specifying a size 
     * @return the position of this vector as the elements in an array of size  corresponding to vSize
     */
	abstract int unfoldOn(Vector vSize);
    /**
     * This method does the inverse operation of unfoldOn(). 
     * @param a position on a one dimensional array
     * @return the corresponding n dimensional position if this vector specifies a size...
     */
    abstract Vector foldInto(int n);

    /**
     * It wraps around this position in a "toroidal" space. See the mechanics of wrapVal, which will be called for each 
     * component of the vector.
     * @param   a Vector specifying a size 
     * @return the Vector after the wrapping
     */
	abstract Vector wrapAround(Vector vSize); 

    /**
     * A simple vector addition, used to transform relative offsets.
     * @param other vector to add 
     * @return
     */
	abstract Vector addition(Vector other);

    /**
     * A general method to get coordinates using indices, instead of for example x,y,z. This is more general and independent
     * of the dimensions of the vector. 
     * @param index of the coordinate (0,1,2,...n)
     * @return the value of the corresponding coordinate of this Vector
     */
	abstract int get(int coord);

	
    /**
     * //This method wraps any value between 0 and max:
     * if (x{@literal <}0) then return max + x 
     * else if (x{@literal >}max) then return the reminder of x/max 
     * When applied to the difference dimensions of a space, it creates a toroidal or n-toroidal space)
     * @param val the value to wrap
     * @param max the maximum value to wrap around
     * @return the wrapped value
     */
	protected static int wrapVal(int val, int max){
		return val < 0 ? (max  - ((-val) % max )) : val >= max  ? val % max  : val;
	} 
}
