//package org.pillar.codec.binary;
//
//import org.pillar.codec.binary.event.DummyInMessage;
//import org.openjdk.jmh.annotations.*;
//
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentSkipListMap;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by pillar on 2015/11/11.
// */
//@State(Scope.Benchmark)
//public class SkipListTest {
//    private ConcurrentSkipListMap<String, DummyInMessage> skipListMap;
//    private DummyInMessage message;
//
//
//    private ConcurrentHashMap<String, DummyInMessage> hashMap;
//
//
//    @Setup
//    public void setup() {
//        skipListMap = new ConcurrentSkipListMap<>();
//        message = new DummyInMessage();
//
//        for (int i = 0; i < 10000; i++) {
//            skipListMap.put(String.valueOf(i), new DummyInMessage());
//            if (i == 500) {
//                skipListMap.put("first", message);
//            }
//        }
//
//        hashMap = new ConcurrentHashMap<>();
//        for (int i = 0; i < 10000; i++) {
//            hashMap.put(String.valueOf(i), new DummyInMessage());
//            if (i == 500) {
//                skipListMap.put("first", message);
//            }
//        }
//
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void delete_from_hashmap() throws Exception {
//        Iterator iterator = hashMap.entrySet().iterator();
//        for (; iterator.hasNext(); ) {
//            Map.Entry entry = (Map.Entry) iterator.next();
//            if (entry.getValue().equals(getMessage())) {
//                iterator.remove();
//            }
//        }
//    }
//
//
//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void delete_from_skip_list() throws Exception {
//        Iterator iterator = skipListMap.entrySet().iterator();
//        for (; iterator.hasNext(); ) {
//            Map.Entry entry = (Map.Entry) iterator.next();
//            if (entry.getValue().equals(getMessage())) {
//                iterator.remove();
//            }
//        }
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void add_into_skip_list() throws Exception {
//        skipListMap.put("1024", new DummyInMessage());
//    }
//
//
//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void add_into_hashmap() throws Exception {
//        hashMap.put("1024", new DummyInMessage());
//    }
//
//
//    public ConcurrentSkipListMap<String, DummyInMessage> getSkipListMap() {
//        return skipListMap;
//    }
//
//    public DummyInMessage getMessage() {
//        return message;
//    }
//}
