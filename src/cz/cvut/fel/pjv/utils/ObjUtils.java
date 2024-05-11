package cz.cvut.fel.pjv.utils;

import com.fasterxml.jackson.annotation.JsonGetter;
import cz.cvut.fel.pjv.item.Item;

import java.util.LinkedList;
import java.util.List;

public class ObjUtils {
    @JsonGetter
    public static List<Item> loadObjects(String path){
        LinkedList<Item> items = new LinkedList<>();
        return items;
    }
}
