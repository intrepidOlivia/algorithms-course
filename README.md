# algorithms-course
Practice code for the Algorithms course at coursera.org

### Union-Find Practice Questions

#### Social Network Connectivity

`Given a social network containing nn members and a log file containing mm timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be m \log nmlogn or better and use extra space proportional to nn.`

Some preliminary thought suggests that while traversing the log, the timestamp may be delivered as soon as the size of any given tree is equal to n.

Some assumptions:
* id[] already exists in a class called Network and is populated with each node acting as its own root.
* size[] already exists, with a length of n, and each value of the array is 1.

public long earliestFullConnection(Network network, File log) {
  FileReader reader = new FileReader(log);  // Disclaimer: I didn't look up the API of an actual file FileReader
  reader.open();

  while (reader.hasNext()) {
    String lineString = reader.nextLine();
    String[] lineData = lineString.split(" ");
    long timeStamp = Long.parseLong(lineData[0]);
    int p = Integer.parseInt(lineData[1]);
    int q = Integer.parseInt(lineData[2]);

    network.join(p, q);
    if (getSize(p) >= n || getSize(q) >= n) {
      return timeStamp;
    }
  }

  return null;
}

#### Union-find with specific canonical element

`Union-find with specific canonical element. Add a method \mathtt{find()}find() to the union-find data type so that \mathtt{find(i)}find(i) returns the largest element in the connected component containing ii. The operations, \mathtt{union()}union(), \mathtt{connected()}connected(), and \mathtt{find()}find() should all take logarithmic time or better.

For example, if one of the connected components is \{1, 2, 6, 9\}{1,2,6,9}, then the \mathtt{find()}find() method should return 99 for each of the four elements in the connected components.`
