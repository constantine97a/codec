//package org.pillar.codec.binary;
//
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//
///**
// * Created by pillar on 2015/11/11.
// */
//public class JMHTest {
//    public static void main(String... args) throws RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(".*" + SkipListTest.class.getSimpleName() + ".*")
//                .forks(1).threads(256)
//                .build();
//
//        new Runner(opt).run();
//    }
//}
