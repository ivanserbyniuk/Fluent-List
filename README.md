# Fluent-List
### For developers who like Kotlin but most work on Java.)
### Java library for working with lists in functional style like in Kotlin. 

Description coming soon...

        //get list of adult users
        List<User> adultUsers = FluentList.from(users)
                .filter(user -> user.age > 18);

        //get name without duplicates
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
