package com.example.joe.talktalk.ui.sidebar;


import com.example.joe.talktalk.model.ContactsModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactsModel> {

    public int compare(ContactsModel o1, ContactsModel o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
