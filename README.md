# Blink App
 This  project that uses Graphql API's with Rx implementation.
 If you are bored with REST API's and create a model class for every new API, go forward! :)

### Libraries
 - [Graphql Apollo Client](https://github.com/apollographql/apollo-android)
 - [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)
 - [Android Data Binding](https://developer.android.com/topic/libraries/data-binding/)
 - [Dagger](https://google.github.io/dagger/)
 - [Glide](https://github.com/bumptech/glide)
 - [Rx](https://github.com/ReactiveX/RxJava)
 - [Lottie](https://github.com/airbnb/lottie-android)

<br/>
<br/>

# So what is the difference between REST?
As I am an Android Developer, I can compare them in client side.Rest is network architectural concept,
 but Graphql is a query language.Apollo Android is a good client framework for Graphql
 
 - In Apollo-Android, you don't have to create your own classes to retrieve network response.
 - Once you define your query like below, it will generate your model class automatically.
 That is really good thing, if you have knowledge  about traditional way.
```
query ApolloRepository($first:Int!){
   getApollo(first:$first) {
    id
    image
    name
  }
}
```
The above query is equalivent of some REST API. This is the way to define a new API in Graphql. And as you see, it's so simple.

# RX implementation
 With Apollo-Rx support dependency, you can use Apollo Calls with Rx. For this, you must add this,
 ```
 implementation "com.apollographql.apollo:apollo-rx2-support:$apolloVersion"
 ```
 This dependency is in the end of the Apollo's github page. So it's pretty hard to find.
 After add this dependecy, the rest of work is below,
  ```
  var observable = Rx2Apollo.from(apolloClient.query(PokemonRepositoryQuery.builder().first(count).build()))
            .map {  
                 it.data()?.data()
            }
   ```
That's it. Now you have your observable and you must just subscribe it and observe the incoming datas :)

# Warning for Dagger2 fans
While setting up the project, you must add the graphql folder under the `app/src` folder.
 You can just put the `API.graphql` file under there and you can use it in proper way. But if you want use dagger, you must put it under the `app/src/graphql/{PACKAGENAME}/` folder. I wasn't careful while I was doing this and I struggled for this issue about 2 days. Dagger does not recognize your auto-generated classes if you don't put them under the `app/src/graphql/{PACKAGENAME}/` folder.
 
