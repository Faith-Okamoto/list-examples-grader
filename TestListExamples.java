import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

class DogChecker implements StringChecker {
    @Override
    public boolean checkString(String s) {
        return s.startsWith("dog");
    }
}

public class TestListExamples {
  @Test
  public void testFilterEmpty() {
      List<String> input = new ArrayList<>();
      StringChecker checker = new DogChecker();
      assertTrue(ListExamples.filter(input, checker).isEmpty());
  }

  @Test
  public void testFilterOneGood() {
      List<String> input = new ArrayList<>(Arrays.asList("dog1"));
      StringChecker checker = new DogChecker();
      assertEquals(new ArrayList<>(Arrays.asList("dog1")),
                    ListExamples.filter(input, checker));
  }

  @Test
  public void testFilterOneBad() {
      List<String> input = new ArrayList<>(Arrays.asList("cat1"));
      StringChecker checker = new DogChecker();
      assertTrue(ListExamples.filter(input, checker).isEmpty());
  }

  @Test
  public void testFilterTwoGood() {
      List<String> input = new ArrayList<>(Arrays.asList("dog1", "dog2"));
      StringChecker checker = new DogChecker();
      assertEquals(new ArrayList<>(Arrays.asList("dog1", "dog2")),
                    ListExamples.filter(input, checker));
  }

  @Test
  public void testFilterTwoHalf() {
      List<String> input = new ArrayList<>(Arrays.asList("dog1", "cat2"));
      StringChecker checker = new DogChecker();
      assertEquals(new ArrayList<>(Arrays.asList("dog1")),
                    ListExamples.filter(input, checker));
  }

  @Test
  public void testFilterLong() {
      List<String> input = new ArrayList<>(
        Arrays.asList("dog1", "dog2", "cat3", "dog4", "cat5", "cat6")
      );
      StringChecker checker = new DogChecker();
      assertEquals(new ArrayList<>(Arrays.asList("dog1", "dog2", "dog4")),
                    ListExamples.filter(input, checker));
  }

  @Test(timeout = 1000)
  public void testMergeEmpty() {
      List<String> list1 = new ArrayList<>();
      List<String> list2 = new ArrayList<>();
      assertTrue(ListExamples.merge(list1, list2).isEmpty());
  }

  @Test(timeout = 1000)
  public void testMergeOnly1() {
      List<String> list1 = new ArrayList<>(Arrays.asList("hi"));
      List<String> list2 = new ArrayList<>();
      assertEquals(new ArrayList<>(Arrays.asList("hi")), 
                    ListExamples.merge(list1, list2));
  }

  @Test(timeout = 1000)
  public void testMergeOnly2() {
      List<String> list1 = new ArrayList<>();
      List<String> list2 = new ArrayList<>(Arrays.asList("hi"));
      assertEquals(new ArrayList<>(Arrays.asList("hi")), 
                    ListExamples.merge(list1, list2));
  }

  @Test(timeout = 1000)
  public void testMergeOnlyOneFirst() {
      List<String> list1 = new ArrayList<>(Arrays.asList("a"));
      List<String> list2 = new ArrayList<>(Arrays.asList("b"));
      assertEquals(new ArrayList<>(Arrays.asList("a", "b")), 
                    ListExamples.merge(list1, list2));
  }

  @Test(timeout = 1000)
  public void testMergeOnlyTwoFirst() {
    List<String> list1 = new ArrayList<>(Arrays.asList("b"));
    List<String> list2 = new ArrayList<>(Arrays.asList("a"));
    assertEquals(new ArrayList<>(Arrays.asList("a", "b")), 
                  ListExamples.merge(list1, list2));
  }

  @Test(timeout = 1000)
  public void testMergeTwoDuplicates() {
    List<String> list1 = new ArrayList<>(Arrays.asList("a"));
    List<String> list2 = new ArrayList<>(Arrays.asList("a"));
    assertEquals(new ArrayList<>(Arrays.asList("a", "a")), 
                  ListExamples.merge(list1, list2));
  }

  @Test(timeout = 1000)
  public void testMergeLong() {
    List<String> list1 = new ArrayList<>(Arrays.asList("b", "d", "e"));
    List<String> list2 = new ArrayList<>(Arrays.asList("a", "c", "f"));
    assertEquals(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f")), 
                  ListExamples.merge(list1, list2));
  }
}
