propel
======

PROPerty based Expression Language for modeling business data and logic.

Propel helps you define your business logic in a pure and portable format and provides tools to execute it in different environments such as a relational databases (SQL), the server's memory (Scala), the client's memory (JavaScript) or even spreadsheets. 

The primary goal is to achieve portability in space and time. You should be able to run your business logic anywhere any time. You should not be tied to a specific technology and be forced to rewrite large systems whenever a new technology emerges. Write the logic once and only touch it if the business requirements change. 

Everything in Propel was designed to fulfill the promise of portability and pure business logic. The language was designed to be independent of the host technology and work in wildly different environments.

Propel builds on an untyped expression tree which is easy to transfer between different environments but also defines a powerful type system that concentrates on modeling real-world problems instead of technology specific issues (like different number representations or collection types).

It provides unparallelled composability by breaking down some of the basic barriers that currently prevent software developers from reuse. It blurs the boundaries between data and logic, type-checking and evaluation, simple and complex types.

It is completely side-effect free and doesn't even try to do any IO. Anything that's left out of the language is provided by the execution environments (including IO). While this might sound limiting it's actually easy to see that you don't need to be concerned with IO in solving business problems.

The language is small enough to be easy to work with, but expressive enough to build whatever you need without getting into the nitty-gritty details of implementation.

By (functional) nature it seemlessly integrates with advanced frameworks like Akka, Apache Spark or any kind of MapReduce solution. 
