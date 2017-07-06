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

import java.lang.IllegalArgumentException;

/**
*  Implementation of the Vector strategy for 3D lattices.
*  @author Pablo Miranda Carranza
*/

public class Vector3D extends Vector{

    int x,y,z;
    Vector3D(int x, int y,int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    int coverSize(){
        return x*y*z;
    }

    int unfoldOn(Vector vSize){
        return z*vSize.get(0)*vSize.get(1) + y*vSize.get(0) + x;
    }

    Vector foldInto(int n){
        int zp=n / (x*y);
        int xyn=n % (x*y);
        int yp=xyn / x;
        int xp=xyn % x;
        return new Vector3D(xp,yp,zp);
    }


    Vector wrapAround(Vector vSize){
        return new Vector3D(wrapVal(x,vSize.get(0)),wrapVal(y,vSize.get(1)),wrapVal(z,vSize.get(2)));
    }

    Vector addition(Vector other){
        return new Vector3D(x+other.get(0), y+other.get(1), z+other.get(2));
    }

    int get(int coord){
        if(coord > 2){
            throw new IllegalArgumentException("trying to get a coordinate number from Vector3D larger than 2");
        }
        return coord == 0 ? x : coord == 1 ? y : z;
    }
    
}
