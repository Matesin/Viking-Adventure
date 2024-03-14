package cz.cvut.fel.pjv;

/**
 * Implementation of the {@link Queue} backed by fixed size array.
 */
public class CircularArrayQueue implements Queue {
    
    private final int capacity;
    private int storedElements;
    private  int head;
    private int tail;
    String[] elements;
    /**
     * Default constructor w/o parameters sets the capacity to 5*/
    public CircularArrayQueue() { 
        capacity = 5;
        assignDefaultValues();
    }

    /**
     * Creates the queue with given {@code capacity}. The capacity represents maximal number of elements that the
     * queue is able to store.
     * @param capacity of the queue
     */
    public CircularArrayQueue(int capacity) {
        this.capacity = capacity;
        assignDefaultValues();
    }
    public int getCapacity(){
        return capacity;
    }
    
    private void assignDefaultValues(){
        storedElements = 0;
        head = 0;
        tail = 0;
        elements = new String[capacity];
    }

    @Override
    public int size() { return storedElements; }

    @Override
    public boolean isEmpty() { return storedElements == 0; }

    @Override
    public boolean isFull() { return storedElements == capacity; }

    @Override
    public boolean enqueue(String obj) { //returns true when an item has been added to the Q, false otherwise
        boolean ret = false;
        if(!isFull()){
            elements[tail] = obj;
            storedElements++;
            tail = (tail + 1) % capacity;
            ret = true;
        }
        return ret;
    }

    @Override
    public String dequeue() {
        String ret = null; //head element
        if (!isEmpty()){
            ret = elements[head];
            elements[head] = null;
            head = (head + 1) % capacity;
            storedElements--;
        }
        return ret;
    }

    @Override
    public void printAllElements() {
        int index = head;
        for (int i = 0; i < storedElements; ++i){
            index = index % capacity;
            System.out.println(elements[index]);
            index++;
        }
    }
}
