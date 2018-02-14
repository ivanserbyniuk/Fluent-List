package com.example.ivan.fluentlist;

import com.ivanserbyniuk.fluentlist.FluentList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 2/13/18.
 */

public class Test {

    public void getNameOfAdultUsers(List<User> users) {
        List<User> adultUsers = FluentList.from(users).filter(user -> user.age > 18);

        List<String> name = FluentList.from(users).map(it -> it.name);

        List<String> namesOfAdultUsers = FluentList.from(users)
                .filter(user -> user.age > 18)
                .map(user -> user.name);

        User neededUser = FluentList.from(users).firstOrNull(user -> user.id == 1);

        List<User> sortedUsers = FluentList.from(users).sortedBy(user -> user.name);

        FluentList.from(users)
                .filter(user -> user.rate > 4)
                .map(user -> user.name)
                .forEachItem(System.out::print);

        FluentList.from(users).forEachIndexes((index, item) -> System.out.print("" + index + " item " + item + ""));

        Map<String, List<User>> groups = FluentList.from(users)
                .sortedBy(user -> user.name)
                .groupBy(user -> String.valueOf(user.name.charAt(0)));

        List<User> result = FluentList.from(groups)
                .filter(it-> !it.getKey().equalsIgnoreCase("j"))
                .flatMap(Map.Entry::getValue);

        String usersText = FluentList.from(users)
                .joinToStringBy(",", it -> "[ " + it.id + " " + it.name + " ] ");

    }

    class User {
        public final long id;
        public final String name;
        public final int rate;
        public final int age;
        public final List<Long> friends = new ArrayList<>();

        public User(long id, String name, int rate, int age) {
            this.id = id;
            this.name = name;
            this.rate = rate;
            this.age = age;
        }
    }
}


