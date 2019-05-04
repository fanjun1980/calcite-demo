- Query String <-> SqlNode <-> RelNode -- RexNode

## SqlNode - Node in the SQL parse tree
SqlNode is the abstract syntax tree that represents the actual structure of the 
query a user input. When a query is first parsed, it's parsed into a SqlNode. 
For example, a SELECT query will be parsed into a SqlSelect with a list of 
fields, a table, a join, etc. Calcite is also capable of generating a query 
string from a SqlNode as well.

## RelNode - Node used in the optimizer
RelNode represents a relational expression - hence "rel." RelNodes are used in 
the optimizer to decide how to execute a query. Examples of relational 
expressions are join, filter, aggregate, etc. Typically, specific 
implementations of RelNode will be created by users of Calcite to represent the 
execution of some expression in their system. When a query is first converted 
from SqlNode to RelNode, it will be made up of logical nodes like 
LogicalProject, LogicalJoin, etc. Optimizer rules are then used to convert from 
those logical nodes to physical ones like JdbcJoin, SparkJoin, CassandraJoin, 
or whatever the system requires. Traits and conventions are used by the 
optimizer to determine the set of rules to apply and the desired outcome.

## RexNode - A row expression
RexNode represents a row expression - hence "Rex" - that's typically contained 
within a RelNode. The row expression contains operations performed on a single 
row. For example, a Project will contain a list of RexNodes that represent the 
projection's fields. A RexNode might be a reference to a field from an input to 
the RedNode, a function call (RexCall), a window (RexOver), etc. The operator 
within the RexCall defines what the node does, and operands define arguments to 
the operator. For example, 1 + 1 would be represented as a RexCall where the 
operator is + and the operands are 1 and 1.

What RelNode and RexNode together give you is a way to plan and implement a 
query. Systems typically use VolcanoPlanner (cost-based) or HepPlanner 
(heuristic) to convert the logical plan (RelNode) into a physical plan and then 
implement the plan by converting each RelNode and RexNode into whatever is 
required by the system for which the plan is generated. The optimizer might 
push down or pull up expressions or reorder joins to create a more optimal 
plan. Then, to implement that plan, e.g. the JDBC integration will convert an 
optimized RelNode back into a SqlNode and then a query string to be executed 
against some database over JDBC. The Spark implementation will convert RelNodes 
into operations on a Spark RDD. The Cassandra implementation will generate a 
CQL query, etc.