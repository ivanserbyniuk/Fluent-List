# Fluent-List
### For developers who like Kotlin but most work on Java.)
### Java library for working with lists in functional style like in Kotlin. 

###Usage

    List<Item> result = FluentList.from(/* array | list | set | map | varargs*/)
        .filter(..)
        .map(..)
         ...
        .distinct()
        .sorted()
  
     FluentList.intRange(0, 10)...
     
### Some practical examples
Description coming soon...

get list of adult users

    List<User> adultUsers = FluentList.from(users)
        .filter(user -> user.age > 18);
                
get list of names of adult users

    List<Long> usersNames = FluentList.from(users)
        .filter(user -> user.age > 18)
        .map(user -> user.name);
                
get sorted list of name of adult users without duplicates
                
    List<Long> usersNames = FluentList.from(users)
        .filter(user -> user.age > 18)
        .map(user -> user.name)
        .distinct()
        .sorted();
                
get users who has books, sorted by age

    List<User> users = FluentList.from(users)
         .filter(user -> !user.getBooks().isEmpty())
         .sortedBy(user-> user.age);
        
find user with id = 1

    User user = fluentList.firstOrNull(user -> user.getId() == 1);

group users by fist letter of theirs name, itch group must be sorted by names: key is first letter, value sorted list of users
with name start from this letter 

    Map<String, List<User>> groups = FluentList.from(users)
        .sortedBy(user -> user.name)
        .groupBy(user -> String.valueOf(user.name.charAt(0)));

get all books form users(all lists of books from all users will concat ) without duplicated.

        List<Book> usersBooks = FluentList.from(users)
                .flatMap(it -> it.getBooks)
                .distinct();
get lists of users who read Frank Gerbert

        List<User> userHowReadFrankGerbert = FluentList.from(users)
                .filter(user -> FluentList.from(user.books)
                        .any(book -> book.author.equalsIgnoreCase("Frank Gerbert")));
                        
create sting by user list 

        String usersLogText = FluentList.from(users)
                .joinToStringBy(",", it -> " [ " + it.age + " " + it.name + " ] ");

iterate lists

        FluentList.from(users)
                .forEachItem(System.out::print);

        FluentList.from(users)
                .forEachIndexes((index, item) -> System.out.print("" + index + " item " + item + ""));
