package br.com.training.pet.utils;

import br.com.training.pet.model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PetSort {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void sort(List<Pet> list) {
        log.info("sorting users");
        Pet temp;
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < list.size()-1; i++) {
                if (compareUser(list.get(i), list.get(i + 1)) > 0) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
        if(!isSorted(list)) {
            log.info("Tring to sort again");
            sort(list);
        }
    }

    // To check if list is sorted or not
    private boolean isSorted(List<Pet> a) {
        for (int i = 0; i < a.size()-1; i++)
            if (compareUser(a.get(i), a.get(i + 1)) > 0)
                return false;
        return true;
    }

    private int compareUser(Pet a, Pet b) {
        return (a.getName()).compareTo(b.getName());
    }
}