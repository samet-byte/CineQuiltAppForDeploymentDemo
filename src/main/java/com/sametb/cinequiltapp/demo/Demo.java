package com.sametb.cinequiltapp.demo;

import com.sametb.cinequiltapp.fav.IFavService;
import com.sametb.cinequiltapp.series.ISeriesService;
import lombok.*;

/**
 * @author Samet Bayat.
 * Date: 3.01.2024 2:03 PM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */



public class Demo {

    @Getter
    @Setter
    public static class DemoWithoutDI {
        private DependencyA dependencyA;
        private DependencyB dependencyB;

        public DemoWithoutDI() {
            this.dependencyA = new DependencyA();
            this.dependencyB = new DependencyB();
        }

        // Other fields and methods

        public void performAction() {
            // Use dependencyA and dependencyB to perform some action
        }
    }
        //.................
   @Data
//   @NoArgsConstructor
//   @RequiredArgsConstructor
//   @RequiredArgsConstructor
    public static class DemoWithDI {
        private DependencyA dependencyA;
        private DependencyB dependencyB;

        public DemoWithDI(DependencyA dependencyA, DependencyB dependencyB) {
            this.dependencyA = dependencyA;
            this.dependencyB = dependencyB;
        }

        // Other fields and methods

        public void performAction() {
            // Use dependencyA and dependencyB to perform some action
        }
    }


    static class DependencyA {
        // Some fields and methods
    }

    static class DependencyB {
        // Some fields and methods
    }
}
