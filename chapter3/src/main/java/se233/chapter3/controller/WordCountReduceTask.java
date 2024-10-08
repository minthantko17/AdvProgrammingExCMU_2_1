package se233.chapter3.controller;

import se233.chapter3.model.FileFreq;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class WordCountReduceTask implements Callable<LinkedHashMap<String, List<FileFreq>>> {
    private Map<String, FileFreq>[] wordMap;
    public WordCountReduceTask(Map<String, FileFreq>[] wordMap){
        this.wordMap = wordMap;
    }

    @Override
    public LinkedHashMap<String, List<FileFreq>> call() throws Exception {
        LinkedHashMap<String, List<FileFreq>> uniqueSets;

        List<Map<String, FileFreq>> wordMapList = new ArrayList<>(Arrays.asList(wordMap));
        uniqueSets = wordMapList.stream()
                .flatMap(m->m.entrySet().stream())
                .collect(Collectors.groupingBy(
                        e->e.getKey(),
                        Collector.of(
                                ()-> new ArrayList<FileFreq>(),
                                (list, item) -> list.add(item.getValue()),
                                (currentList, newItems) -> {
                                    currentList.addAll(newItems);
                                    return currentList; },
                                lst->{
                                    Collections.sort(lst, (s1, s2)->Integer.compare(s2.getFreq(), s1.getFreq()));
                                    return lst;
                                })

                ))
                .entrySet()
                .stream()
                .sorted((a,b)->{
                    int num1=a.getValue().stream().mapToInt(num->num.getFreq()).sum();
                    int num2=b.getValue().stream().mapToInt(num->num.getFreq()).sum();
                    return Integer.compare(num2, num1);
                })
                .collect(Collectors.toMap(e->e.getKey(), e->e.getValue(), (v1,v2)->v1, ()->new LinkedHashMap<>()));
                    // mapFactory – a supplier providing a new empty Map into which the results will be inserted
        return uniqueSets;
    }

//    public LinkedHashMap<String, List<FileFreq>> getUniqueSets() {return uniqueSets;}
//    public void setUniqueSets(LinkedHashMap<String, List<FileFreq>> uniqueSets) {this.uniqueSets = uniqueSets;}
}
