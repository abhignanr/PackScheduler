package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedQueue
 * @author davidb
 * @author maisa little
 * Test class for LinkedQueue
 */
public class LinkedQueueTest {

	/** Queue for testing purposes*/
	Queue<String> q;
	
	/** default capacity */
	static final int CAPACITY = 8;
	
	/**
	 * Initializes the queue
	 */
	@BeforeEach
	void setUp()  {
		q = new LinkedQueue<String>(CAPACITY);
	}

	/**
	 * tests LinkedQueue.enqueue
	 */
	@Test
	void testEnqueue() {
		for (int i = 0; i < CAPACITY; i++) {
			q.enqueue(i + "");
		}
		assertThrows(IllegalArgumentException.class, () -> q.enqueue("Should be too much"));
	}

	/**
	 * tests LinkedQueue.dequeue
	 */
	@Test
	void testDequeue() {
		assertThrows(NoSuchElementException.class, () -> q.dequeue());
		String item = "test";
		q.enqueue(item);
		assertEquals(item, q.dequeue());
		assertThrows(NoSuchElementException.class, () -> q.dequeue());
	}

	/**
	 * tests LinkedQueue.isEmpty
	 */
	@Test
	void testIsEmpty() {
		assertTrue(q.isEmpty());
		q.enqueue("test");
		assertFalse(q.isEmpty());
	}

	/**
	 * LinkedQueue.size
	 */
	@Test
	void testSize() {
		assertEquals(0, q.size());
		for (int i = 1; i < CAPACITY; i++) {
			q.enqueue("" + i);
			assertEquals(i, q.size());
		}
	}

	/**
	 * LinkedQueue.setCapacity
	 */
	@Test
	void testSetCapacity() {
		q.setCapacity(CAPACITY + 1);
		
		assertThrows(IllegalArgumentException.class, () -> q.setCapacity(-1));
		q.enqueue("one");
		q.enqueue("two");
		assertThrows(IllegalArgumentException.class, () -> q.setCapacity(1));
		
	}

}