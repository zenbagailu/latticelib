package lattice;
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

import java.lang.IllegalArgumentException;

/**
*  Implementation of the Vector strategy for 2D.
*  @author Pablo Miranda Carranza
*/

public class Vector2D extends Vector {

	int x,y;
	Vector2D(int x, int y){
		this.x=x;
		this.y=y;
	}

	int coverSize(){
		return x*y;
	}

	int unfoldOn(Vector vSize){
		return y*vSize.get(0) + x;
	}

    Vector foldInto(int n){
        int yp=n / x;
        int xp=n % x;
        return new Vector2D(xp,yp);
    }

	Vector wrapAround(Vector vSize){
		return new Vector2D(wrapVal(x,vSize.get(0)),wrapVal(y,vSize.get(1)));
	}

	Vector addition(Vector other){
		return new Vector2D(x+other.get(0), y+other.get(1));
	}

	int get(int coord){
		if(coord > 1){
			throw new IllegalArgumentException("trying to get a coordinate number from Vector2D larger than 1");
		}
		return coord == 0 ? x : y;
	}
    
}
