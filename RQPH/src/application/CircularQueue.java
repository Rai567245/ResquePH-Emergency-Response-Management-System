package application;

public class CircularQueue {

	private Patient[] arr;
	private int front, rear, size, count;
	
	public CircularQueue(int size) {
		this.size = size;
		arr = new Patient[size];
		front = 0; rear = -1; count = 0;
	}
	public boolean enqueue(Patient p) {
		if (isFull()) return false;
		rear = (rear + 1) % size;
		arr[rear] = p;
		count ++;
		return true;
	}
	public Patient dequeue() {
		if(isEmpty()) return null;
		
		Patient p = arr[front];
		front = (front + 1) % size;
		count--;
		return p;
	}
	public Patient peek() {
		if (isEmpty()) return null;
		return arr[front];
	}
	public boolean isEmpty() {
		return count == 0;
	}
	public boolean isFull() {
		return count == size;
	}
	public int getCount() {
		return count;
	}
	public Patient getAt(int index) {
		if (index >= count) return null;
		return arr[(front + index) % size];
	}
	public void clear() {
		front = 0; rear =-1; count = 0;
	}
	

}