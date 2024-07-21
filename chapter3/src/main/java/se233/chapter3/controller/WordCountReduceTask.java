package se233.chapter3.controller;

import se233.chapter3.model.FileFreq;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class WordCountReduceTask {
    private LinkedHashMap<String, List<FileFreq>> uniqueSets;
    public WordCountReduceTask(Map<String, FileFreq>[] wordMap){
        List<Map<String, FileFreq>> wordMapList = new ArrayList<>(Arrays.asList(wordMap));
        this.uniqueSets = wordMapList.stream()
                .flatMap(m->m.entrySet().stream())
                .collect(Collectors.groupingBy(
                        e->e.getKey(),
                        Collector.of(
                                ()-> new ArrayList<FileFreq>(),
                                (list, item) -> list.add(item.getValue()),
                                (currentList, newItems) -> {
                                    currentList.addAll(newItems);
                                    return currentList; })
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(e->e.getKey(), e->e.getValue(), (v1,v2)->v1, ()->new LinkedHashMap<>()));
    }

    public LinkedHashMap<String, List<FileFreq>> getUniqueSets() {return uniqueSets;}
    public void setUniqueSets(LinkedHashMap<String, List<FileFreq>> uniqueSets) {this.uniqueSets = uniqueSets;}
}
