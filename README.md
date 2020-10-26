# Enhanced GraphQL - Currently work in progress

Filtering language to improve GraphQL filtering capabilities

## Getting Started

```bash
git clone https://github.com/Overflow404/EnhancedGraphQL
```

## Adding as a Maven dependency:
```maven
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.Overflow404</groupId>
    <artifactId>EnhancedGraphQL</artifactId>
    <version>master-82e5f18438-1</version>
</dependency>
```
## Example
Let's start creating a Spring Boot project:

```java
@SpringBootApplication
@EnableJpaRepositories
public class GraphqlFilterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlFilterDemoApplication.class, args);
    }

}
```

Let's add a new GraphQL Provider with a well-defined runtime wiring for two data fetchers:
```java
public RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
            .type(newTypeWiring("Query")
                    .dataFetcher("searchPerson", personDataFetcher.searchPerson())
                    .dataFetcher("searchRole", roleDataFetcher.searchRole()))
            .build();
}
```

Let's introduce the two entity:
```java
@Entity
@Table(name = "Person")
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "id")
    @Id
    private Integer id;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Column(name = "firstName")
    private String firstName;
    
    .
    .
    .
    
    @OneToOne(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "person",
            orphanRemoval = true)
    private Role role;


    .
    .
    .
}

```

```java
@Entity
@Table(name = "Role")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "id")
    @Id
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "priority")
    private Integer priority;
    
    @OneToOne
    @JoinColumn(
            name = "id",
            referencedColumnName = "id"
    )
    private Person person;

    .
    .
    .

}
```
And the repositories:
```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
}
```

```java
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
}
```

And now, the core part, let's write the data fetcher!
```java
@Component
public class PersonDataFetcher {

    private static final Parser<Person> personParser = AbstractSyntaxTreeParser.getInstance();
    private static final Parser<Role> roleParser = AbstractSyntaxTreeParser.getInstance();

    public DataFetcher<List<Person>> searchPerson() {

        return dataFetchingEnvironment -> {
            PathSpecification<Person> personSpecification = personParser
                    .parse(dataFetchingEnvironment.getArgument("personFilter"))
                    .getSpecification();

            PathSpecification<Role> roleSpecification = roleParser
                    .parse(dataFetchingEnvironment.getArgument("roleFilter"))
                    .getSpecification();

            return repository.findAll(
                    personSpecification
                            .atRoot()
                            .and(
                                    roleSpecification
                                            .atPath(Person_.role)
                            )
            );

        };
    }
    
}
```

GraphQL schema used:
```graphql
type Query {
    searchPerson(personFilter: PersonFilter, roleFilter: RoleFilter): [Person]
    searchRole(roleFilter: RoleFilter): [Role]
}

# Define the types
type Person {
    id: Int
    firstName: String
    lastName: String
    address: String
    city: String
    age: Int
    floatTest: Float
    startDate: String
    enabled: Boolean
    role: Role
}

type Role {
    id: Int
    name: String
    description: String
    priority: Int
}

# Define filter input
input PersonFilter {
    id: IntExpression
    firstName: StringExpression
    lastName: StringExpression
    address: StringExpression
    city: StringExpression
    age: IntExpression
    floatTest: FloatExpression
    startDate: DateExpression
    enabled: BooleanExpression

    and: [PersonFilter!]
    or: [PersonFilter!]
    not: PersonFilter
}

input RoleFilter {
    id: IntExpression
    name: StringExpression
    description: StringExpression
    priority: IntExpression

    and: [RoleFilter!]
    or: [RoleFilter!]
    not: RoleFilter
}

# Define String expression
input StringExpression {
    equals: String
    like: String
    in: [String!]
}

# Define Int Expression
input IntExpression {
    eq: Int
    gt: Int
    gte: Int
    lt: Int
    lte: Int
}

# Define Float Expression
input FloatExpression {
    eq: Float
    gt: Float
    gte: Float
    lt: Float
    lte: Float
}

input DateExpression {
    exactly: String
    before: String
    beforeOrEquals: String
    after: String
    afterOrEquals: String
}

input BooleanExpression {
    is: Boolean
}

```

Launching our project we have a custom filtering language that automatically translates our filtering language to JPA specifications!

Here is an example with a query to search a Person with specific filters (also on foreign linked entities):

```graphql
query SearchPerson {
  searchPerson(
    personFilter: {
      and: [
        { not: { age: { eq: 18 } } }
        { startDate: { exactly: "2020-10-26" } }
      ]
    }
    ,
    roleFilter: {
      or: [
        {name: {equals: "Role2"}},
        {name: {equals: "Role3"}}
      ]
    }
  ) {
    id
    firstName
    lastName
    address
    city
    age
    registrationDate
    enabled
    role {
      name
    }
  }
}

```

The response:
```json
{
  "data": {
    "searchPerson": [
      {
        "id": 2,
        "firstName": "Test name 1",
        "lastName": "Test lastName 1",
        "address": "Test address1",
        "city": "City1",
        "age": 23,
        "registrationDate": "2020-10-26",
        "enabled": false,
        "role": {
          "name": "Role2"
        }
      },
      {
        "id": 3,
        "firstName": "Test name 2",
        "lastName": "Test lastName 2",
        "address": "Test address 2",
        "city": "City2",
        "age": 20,
        "registrationDate": "2020-10-26",
        "enabled": true,
        "role": {
          "name": "Role3"
        }
      }
    ]
  }
}


```

# TODO
* **Test** 
* **Logging** 

## Authors

* **Overflow404** 
