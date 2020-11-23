package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Organization;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Organization.Experience position) {
        return DateUtil.format(position.getStartTime()) + " - " + DateUtil.format(position.getEndTime());
    }
}

