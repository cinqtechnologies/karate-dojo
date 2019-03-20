package br.com.cinq.dojo.karate;

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;

// * A linked list node...
// * Each item in the node has an additional pointer 'random' which points to a random node in the list.
// => Create a copy of the list =)

// A--->B--->C--->D
// |---------|
//      |---------|
//      |----|
// |<-------------|
@EqualsAndHashCode (of = "uniqueness")
class Node {

  private final int id;

  public Node random;

  public Node next;

  private Object unique = new Object();

  public Node(int id) {
    this.id = id;
  }

  public String getUniqueness() {
    return this.toString();
  }

  public Node copy() {
    Node copy = new Node(id);
    Map<Object, Node> existingNodes = new HashMap<>();

    existingNodes.put(this.unique, copy);
    Node newRandom = random.copy(existingNodes);
    Node newNext = next.copy(existingNodes);

    copy.random = newRandom;
    copy.next = newNext;
    return copy;
  }

  private Node copy(Map<Object, Node> existingNodes) {
    Node copy = new Node(id);

    existingNodes.put(this.unique, copy);

    Node newRandom;
    if (existingNodes.containsKey(random.unique)) {
      newRandom = existingNodes.get(random.unique);
    } else {
      newRandom = random.copy(existingNodes);
    }

    Node newNext;
    if (existingNodes.containsKey(next.unique)) {
      newNext = existingNodes.get(next.unique);
    } else {
      newNext = next.copy(existingNodes);
    }

    copy.random = newRandom;
    copy.next = newNext;

    return copy;
  }

  @Override
  public String toString() {
    Map<Object, String> existingNodes = new HashMap<>();

    String thisToString = String.format("Node(%d, next=%d, random=%d)", id,
        next == null ? "null" : next.id,
        random == null ? "null" : random.id);
    existingNodes.put(this.unique, thisToString);

    String randomToString = random.toString(existingNodes);
    String nextToString = next.toString(existingNodes);

    return String.format("%s,Random(%s),Next(%s)", thisToString, randomToString, nextToString);
  }

  private String toString(Map<Object, String> existingNodes) {
    String thisToString = String.format("Node(%d, next=%s, random=%s)", id,
        next == null ? "null" : next.id,
        random == null ? "null" : random.id);
    existingNodes.put(this.unique, thisToString);

    String randomToString =
        existingNodes.containsKey(random.unique) ? existingNodes.get(random.unique)
            : random.toString(existingNodes);
    String nextToString = existingNodes.containsKey(next.unique) ? existingNodes.get(next.unique)
        : next.toString(existingNodes);

    return String.format("%s,Random(%s),Next(%s)", thisToString, randomToString, nextToString);
  }
}
