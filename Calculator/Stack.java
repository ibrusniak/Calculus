
public class Stack<E> {
    
    private final int STEP = 5;
    private Object[] elemets = new Object[STEP];
    
    public Stack() {}
    
    public void push(E e) {
        int size = size();
        if (size == 0) {
            elemets[0] = e;
            return;
        }
        
        int capacity = capacity();
        int newCapacity = size == capacity ? capacity + STEP : capacity;
        
        Object[] dest = new Object[newCapacity];
        System.arraycopy(elemets, 0, dest, 1, size);
        dest[0] = e;
        elemets = dest;
    }
    
    @SuppressWarnings("unchecked")
    public E pop() {
        int size = size();
        if (size == 0) {
            return null;
        }
        
        E res = (E) elemets[0];
        
        int capacity = capacity();
        int newCapacity = capacity - size - 1 > STEP ? capacity - STEP : capacity;
        
        Object[] dest = new Object[newCapacity];
        System.arraycopy(elemets, 1, dest, 0, size);
        elemets = dest;
        
        return res;
    }
    
    @SuppressWarnings("unchecked")
    public E peek() {

        if (size() == 0) {
            return null;
        }
        return (E) elemets[0];
    }

    public void clear() {
        elemets = new Object[STEP];
    }

    private int size() {
        int count = 0;
        for (int i = 0; i < elemets.length; i++) {
            if (elemets[i] == null) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }
    
    private int capacity() {
        return elemets.length;
    }
    
    @Override
    public String toString() {
        String res = "";
        boolean first = true;
        for (int i = 0; i < elemets.length; i++) {
            if (elemets[i] != null) {
                res += (first ? "" : ",") + elemets[i].toString();
                first = false;
            }
        }
        return String.format("[%s] size: %s, capacity: %s"
            , res, size(), capacity());
    }

    // TODO: Remove this method
    /**
     * For testing purpose only
     * @param args
     */
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 1; i <= 9; i++) {
            stack.push(i);
            System.out.println(stack);
        }

        System.out.println();

        for (int i = 1; i <= 9; i++) {
            stack.pop();
            System.out.println(stack);
        }

        System.out.println();
        System.out.println(stack.peek());
        stack.push(20);
        stack.push(30);
        System.out.println(stack.peek());
        System.out.println(stack);
        stack.clear();
        System.out.println(stack);
        System.out.println(stack.pop());
    }
}
    
