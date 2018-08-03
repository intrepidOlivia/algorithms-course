# algorithms-course
Practice code for the Algorithms course at coursera.org

### Union-Find Practice Questions

#### Social Network Connectivity

`
Given a social network containing nn members and a log file containing mm timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be m \log nmlogn or better and use extra space proportional to nn.
`

Some preliminary thought suggests that while traversing the log, the timestamp may be delivered as soon as the size of any given tree is equal to n.