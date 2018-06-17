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
package org.mapstruct.ap.test.info.annotations;

public class SlotMachineDto {

    public static class ConsoleDto {

        private Sign sign1;
        private Sign sign2;
        private Sign sign3;
        private int coinage;

        public Sign getSign1() {
            return sign1;
        }

        public void setSign1(Sign sign1) {
            this.sign1 = sign1;
        }

        public Sign getSign2() {
            return sign2;
        }

        public void setSign2(Sign sign2) {
            this.sign2 = sign2;
        }

        public Sign getSign3() {
            return sign3;
        }

        public void setSign3(Sign sign3) {
            this.sign3 = sign3;
        }

        public int getCoinage() {
            return coinage;
        }

        public void setCoinage(int coinage) {
            this.coinage = coinage;
        }
    }

    private ConsoleDto console;

    public ConsoleDto getConsole() {
        return console;
    }

    public void setConsole(ConsoleDto console) {
        this.console = console;
    }
}
