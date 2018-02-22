# Fluent-List
### For developers who like Kotlin but must work on Java.)
### Java library for working with lists in functional style like in Kotlin. 
### Install 

Add to build.gradle

    dependencies {
       .......
       compile 'com.ivanserbyniuk.retro:fluent-list:0.2.6@aar'
    }
    repositories {
        maven {
             url  "https://dl.bintray.com/ivanserbyniuk/retro"
        }
    }
### Usage

    List<Item> result = FluentList.from(/* array | list | set | map | varargs*/)
        .filter(..)
        .map(..)
         ...
        .distinct()
        .sorted()
  
     FluentList.intRange(0, 10)...
     
### Some practical examples

- get list of adult users


      List<User> adultUsers = FluentList.from(users)
        .filter(user -> user.age > 18);
                
- get list of names of adult users


      List<String> usersNames = FluentList.from(users)
        .filter(user -> user.age > 18)
        .map(user -> user.name);
                
- get sorted list of name of adult users without duplicates
                
                
      List<String> usersNames = FluentList.from(users)
        .filter(user -> user.age > 18)
        .map(user -> user.name)
        .distinct()
        .sorted();
                
- get users who has books, sorted by age


      List<User> users = FluentList.from(users)
         .filter(user -> !user.getBooks().isEmpty())
         .sortedBy(user-> user.age);
        
- find user with id = 1


    User user = fluentList.firstOrNull(user -> user.getId() == 1);

- group users by fist letter of theirs name, itch group must be sorted by names: key is first letter, value sorted list of users
with name start from this letter 


    Map<String, List<User>> groups = FluentList.from(users)
        .sortedBy(user -> user.name)
        .groupBy(user -> String.valueOf(user.name.charAt(0)));

- get all books form users(all lists of books from all users will concat ) without duplicated.


      List<Book> usersBooks = FluentList.from(users)
        .flatMap(it -> it.getBooks)
        .distinct();
- get lists of users who read Frank Gerbert


      List<User> userHowReadFrankGerbert = FluentList.from(users)
        .filter(user -> FluentList.from(user.books)
            .any(book -> book.author.equalsIgnoreCase("Frank Gerbert")));
                        
- create string by user list 


      String usersLogText = FluentList.from(users)
        .joinToStringBy(",", it -> " [ " + it.age + " " + it.name + " ] ");

- iterate lists


      FluentList.from(users)
        .forEachItem(System.out::print);

      FluentList.from(users)
        .forEachIndexes((index, item) -> System.out.print("" + index + " item " + item + ""));


## See also

- any 
 
 
        boolean withPriceMoreThen50 = FluentList.from(products).any(it -> it.getPrice() > 50);
        assertTrue(withPriceMoreThen50);

        boolean withPriceMoreThen150 = FluentList.from(products).any(it -> it.getPrice() > 150);
        assertFalse(withPriceMoreThen150);

- all


        boolean allItemsCheaperThen100 = FluentList.from(products).all(it -> it.getPrice() < 100);
        assertTrue(allItemsCheaperThen100);

        boolean allItemsCheaperThen10 = FluentList.from(products).all(it -> it.getPrice() < 10);
        assertFalse(allItemsCheaperThen10);
    
- non 


        boolean noPriceMoreThen100 = FluentList.from(products).non(it -> it.getPrice() > 100);
        assertTrue(noPriceMoreThen100);

        boolean noPirceLessThen100 = FluentList.from(products).non(it -> it.getPrice() < 100);
        assertFalse(noPirceLessThen100);
    
- minBy

 
        Product cheapestProduct = FluentList.from(products).minBy(Product::getPrice);
        assertEquals("prod5", cheapestProduct.getName());


- maxBy
 
 
        Product mostExpensiveProduct = FluentList.from(products).maxBy(Product::getPrice);
        assertEquals("prod4", mostExpensiveProduct.getName());
 

- sumBy


        int priceForAllProds = FluentList.from(products).sumBy(Product::getPrice);
        assertEquals(78, priceForAllProds);
 
   ## To be continued
