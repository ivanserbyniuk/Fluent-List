package com.example.ivan.fluentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ivanserbyniuk.fluentlist.FluentList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 2/14/18.
 */

public class MainActivity
        extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fluentListDemo(Arrays.asList());
    }

    public void fluentListDemo(List<User> users) {
        //get list of adult users
        List<User> adultUsers = FluentList.from(users)
                .filter(user -> user.age > 18);

        //get name with out duplicates
        List<String> names = FluentList.from(users)
                .map(it -> it.name)
                .distinct();
        List<Long> idOfAdultUsers = FluentList.from(users)
                .filter(user -> user.age > 18)
                .map(user -> user.id);

        User findUser = FluentList.from(users)
                .firstOrNull(user -> user.id == 1);

        List<User> sortedUsers = FluentList.from(users)
                .sortedBy(user -> user.name);

        Map<String, List<User>> groups = FluentList.from(users)
                .sortedBy(user -> user.name)
                .groupBy(user -> String.valueOf(user.name.charAt(0)));

        List<Book> usersBooks = FluentList.from(users)
                .flatMap(it -> it.books)
                .distinct();

        List<User> userHowReadFrankGerbert = FluentList.from(users)
                .filter(user -> FluentList.from(user.books)
                        .any(book -> book.author.equalsIgnoreCase("Frank Gerbert")));

        String usersLogText = FluentList.from(users)
                .joinToStringBy(",", it -> "[ " + it.id + " " + it.name + " ] ");

        FluentList.from(users)
                .map(user -> user.name)
                .forEachItem(System.out::print);

        FluentList.from(users)
                .forEachIndexes((index, item) -> System.out.print("" + index + " item " + item + ""));

    }

    class User {
        public final long id;
        public final String name;
        public final int age;
        private List<Book> books = new ArrayList<>();

        public User(long id, String name, int rate, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    class Book {
        public final long id;
        public final String name;
        public final String author;
        public final String price;

        public Book(long id, String name, String author, String price) {
            this.id = id;
            this.name = name;
            this.author = author;
            this.price = price;
        }
    }
}
