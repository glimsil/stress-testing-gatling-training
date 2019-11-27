package br.com.training.user.utils;

import br.com.training.user.model.User;
import br.com.training.user.rest.dto.UserResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserSort {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void sortUsers(List<UserResponseDTO> list) {
        log.info("sorting users");
        UserResponseDTO temp;
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
            sortUsers(list);
        }
    }

    // To check if list is sorted or not
    private boolean isSorted(List<UserResponseDTO> a) {
        for (int i = 0; i < a.size()-1; i++)
            if (compareUser(a.get(i), a.get(i + 1)) > 0)
                return false;
        return true;
    }

    private int compareUser(UserResponseDTO a, UserResponseDTO b) {
        return (a.getFirstName() + a.getLastName()).compareTo(b.getFirstName() + b.getLastName());
    }
}