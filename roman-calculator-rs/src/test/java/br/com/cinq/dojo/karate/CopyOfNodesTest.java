package br.com.cinq.dojo.karate;

// * A linked list node...
// * Each item in the node has an additional pointer 'random' which points to a random node in the list.
// => Create a copy of the list =)

// A--->B--->C--->D
// |---------|
//      |---------|
//      |----|
// |<-------------|

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CopyOfNodesTest {

  @Test
  public void testCopyOfNodes() {
    int limit = 1000;
    List<Node> nodes = IntStream.iterate(0, i -> i + 1)
        .limit(limit)
        .mapToObj(Node::new)
        .collect(Collectors.toList());

    IntStream.iterate(0, i -> i + 1)
        .limit(limit)
        .peek(i -> nodes.get(i).next = nodes.get((i + 1) % nodes.size()))
        .forEach(i -> nodes.get(i).random = nodes.get(new Random().nextInt(nodes.size())));

    System.out.println("nodes.get(0).toString() = " + nodes.get(0).toString());
    System.out.println("nodes.get(0).copy().toString() = " + nodes.get(0).copy().toString());
    Assert.assertEquals(nodes.get(0), nodes.get(0).copy());
  }
}