/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedbeans.multiplecollections;

import java.util.List;

import org.mapstruct.ap.test.nestedbeans.Car;

public class Garage {
    private List<Car> cars;
    private List<Car> usedCars;

    public Garage() {
    }

    public Garage(List<Car> cars, List<Car> usedCars) {
        this.cars = cars;
        this.usedCars = usedCars;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Car> getUsedCars() {
        return usedCars;
    }

    public void setUsedCars(List<Car> usedCars) {
        this.usedCars = usedCars;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Garage garage = (Garage) o;

        if ( cars != null ? !cars.equals( garage.cars ) : garage.cars != null ) {
            return false;
        }
        return usedCars != null ? usedCars.equals( garage.usedCars ) : garage.usedCars == null;

    }

    @Override
    public int hashCode() {
        int result = cars != null ? cars.hashCode() : 0;
        result = 31 * result + ( usedCars != null ? usedCars.hashCode() : 0 );
        return result;
    }
}
