import java.util.Comparator;
import java.util.ListIterator;
import java.util.Iterator;

public class Test{
    private static int testNum = 0;
    public static void main(String[] args){
        System.out.println("\nTesting the ArrayList\n==================================================");
        List<Integer> alist = new ArrayList<>();
        testList(alist);

        System.out.println("\n\nTesting the LinkedList\n==================================================");
        List<Integer> llist = new LinkedList<>();
        testList(llist); 
    }
    public static void testList(List<Integer> list){
        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: add methods: add(E), add(int,E), addFirst(E), addLast(E)");
        list.add(11);
        list.add(22);
        list.add(1, 33);
        list.addFirst(44);
        list.add(55);
        list.add(3, 66);
        list.addLast(77);
        list.add(5, 88);
        System.out.println("list: " + list);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: clone method");
        List<Integer> copylist = clone(list);
        System.out.println(list + ".clone() = " + copylist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: Collection methods: addAll, containsAll, retainAll, removeAll");
        List<Integer> nlist = null;
        if(list instanceof ArrayList)
            nlist = new ArrayList<>();
        else if(list instanceof LinkedList)
            nlist = new LinkedList<>();
        nlist.add(111);
        nlist.add(77);
        nlist.add(222);
        nlist.add(55);
        copylist = clone(nlist);
        System.out.print(copylist + ".addAll(" + list + ") = ");
        copylist.addAll(list);
        System.out.println(copylist);

        copylist = clone(list);
        System.out.print(copylist + ".retainAll(" + nlist + ") = ");
        copylist.retainAll(nlist);
        System.out.println(copylist);

        copylist = clone(list);
        System.out.print(copylist + ".removeAll(" + nlist + ") = ");
        copylist.removeAll(nlist);
        System.out.println(copylist);

        copylist = clone(list);
        System.out.println(list + ".containsAll(" + copylist + ") = " + list.containsAll(copylist));
        list.removeFirst();
        System.out.println(list + ".containsAll(" + copylist + ") = " + list.containsAll(copylist));

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: (getters) get(int), getFirst(), getLast()");
        System.out.println(list);
        System.out.println(list + ".get(3) = " + list.get(3));
        System.out.println(list + ".getFirst() = " + list.getFirst());
        System.out.println(list + ".getLast() = " + list.getLast());
        
        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: set method");
        System.out.print(list);
        System.out.println(".set(3, 44) = " + list.set(3, 44));
        System.out.println("Modified list : " + list);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: (remove methods) remove(Object), remove(int), removeFirst(), removeLast()");
        Integer i = 55;
        System.out.println("list: " + copylist);
        System.out.println("remove(55) = " + copylist.remove(i));
        System.out.println("removeFirst() = " + copylist.removeFirst());
        System.out.println("removeLast() = " + copylist.removeLast());
        System.out.println("remove(2) = " + copylist.remove(2));
        System.out.println("Modified list: " + copylist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: (search methods) contains(Object), indexOf(Object), lastIndexOf(Object)");
        System.out.println(list + ".contains(88) = " + list.contains(88));
        System.out.println(list + ".contains(111) = " + list.contains(111));
        System.out.println(list + ".indexOf(55) = " + list.indexOf(55));
        System.out.println(list + ".indexOf(222) = " + list.indexOf(222));
        System.out.println(list + ".lastIndexOf(77) = " + list.lastIndexOf(77));
        System.out.println(list + ".lastIndexOf(222) = " + list.lastIndexOf(222));
        System.out.println("Unmodified list: " + list);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: (iterator methods) iterator, listIterator, listIterator(int)");
        System.out.println("list: " + list);
        Iterator<Integer> iter = list.iterator();
        System.out.print("Iterator from 0 to " + (list.size()-1) + ": ");
        while(iter.hasNext()){
            System.out.print(iter.next() + " ");
        }
        System.out.println();

        ListIterator<Integer> liter = list.listIterator();
        System.out.print("ListIterator from 0 to " + (list.size()-1) + ", then from " + (list.size()-1) + " to 0: ");
        while(liter.hasNext()){
            System.out.print(liter.next() + " ");
        }
        while(liter.hasPrevious()){
            System.out.print(liter.previous() + " ");
        }
        System.out.println();
        System.out.print("ListIterator from 3 to 0" + ", then from 0 to " + (list.size()-1) + ": ");
        liter = list.listIterator(4);
        while(liter.hasPrevious()){
            System.out.print(liter.previous() + " ");
        }
        while(liter.hasNext()){
            System.out.print(liter.next() + " ");
        }
        System.out.println();


        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: equals method");
        System.out.println(list + ".equals(" + nlist + ") ? " + list.equals(nlist));
        System.out.println(list + ".equals(" + list + ") ? " + list.equals(list));


        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: sort method");
        System.out.println("list: " + list);
        list.sort(null);
        System.out.println("list sorted using the natural ordering: " + list);

        nlist.clear();
        nlist.add(1000); nlist.add(900); nlist.add(1500); nlist.add(33); nlist.add(555); nlist.add(88);
        Comparator<Integer> c = new Comparator<Integer>(){
            public int compare(Integer i1, Integer i2){
                int l1 = i1.toString().length();
                int l2 = i2.toString().length();
                if(l1 != l2)
                    return l1 - l2;
                return i2.compareTo(i1);
            }
        };
        System.out.println("\nlist: " + nlist);
        nlist.sort(c);
        System.out.println("list sorted using the number of digits (ties: descending value): " + nlist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: toArray method");
        Object[] array = list.toArray();
        System.out.print(list + ".toArray() = [");
        for(Object o: array){
            System.out.print(o + " ");
        }
        System.out.println("]");

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: reversed method");
        System.out.println("list: " + nlist);
        List<Integer> rlist = nlist.reversed();
        System.out.println("reversed list: " + rlist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: subList method");
        System.out.println("list: " + nlist);
        List<Integer> slist = nlist.subList(0, nlist.size());
        System.out.println("sublist from 0 to " + nlist.size() + ": " + slist);
        slist = nlist.subList(1, nlist.size()/2);
        System.out.println("sublist from 1 to " + (nlist.size()/2) + ": " + slist);
        try{
            System.out.print("sublist from 5 to 2: " );
            slist = nlist.subList(5, 2);
            System.out.println(slist);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        slist = nlist.subList(3, 3);
        System.out.println("sublist from 3 to 3: " + slist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: size and isEmpty methods");
        List<Integer> elist = newList(list);
        System.out.println(elist + ".size() = " + elist.size());
        System.out.println(elist + ".isEmpty() = " + elist.isEmpty());
        System.out.println(list + ".size() = " + list.size());
        System.out.println(list + ".isEmpty() = " + list.isEmpty());
        elist.add(5);
        System.out.println(elist + ".size() = " + elist.size());
        System.out.println(elist + ".isEmpty() = " + elist.isEmpty());
        elist.removeFirst();
        System.out.println(elist + ".size() = " + elist.size());
        System.out.println(elist + ".isEmpty() = " + elist.isEmpty());

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: clear method");
        copylist = clone(list);
        System.out.print(copylist + ".clear() = ");
        copylist.clear();
        System.out.println(copylist);
        System.out.println("size = " + copylist.size() + ", isEmpty = " + copylist.isEmpty());
        copylist.add(1); copylist.addFirst(2); copylist.addLast(3);
        System.out.println("list after adding 1, addFirst(2), addLast(3): " + copylist);
        System.out.println("Unmodified original list: " + list);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: exceptions for invalid indices and empty lists");
        System.out.println("list: " + list);
        try{ list.get(-1); System.out.println("get(-1): no exception"); }
        catch(RuntimeException e){ System.out.println("get(-1): " + e.getClass().getSimpleName()); }
        try{ list.get(list.size()); System.out.println("get(" + list.size() + "): no exception"); }
        catch(RuntimeException e){ System.out.println("get(" + list.size() + "): " + e.getClass().getSimpleName()); }
        try{ list.set(list.size(), 0); System.out.println("set(" + list.size() + ", 0): no exception"); }
        catch(RuntimeException e){ System.out.println("set(" + list.size() + ", 0): " + e.getClass().getSimpleName()); }
        try{ list.add(list.size() + 1, 0); System.out.println("add(" + (list.size() + 1) + ", 0): no exception"); }
        catch(RuntimeException e){ System.out.println("add(" + (list.size() + 1) + ", 0): " + e.getClass().getSimpleName()); }
        try{ list.remove(-1); System.out.println("remove(-1): no exception"); }
        catch(RuntimeException e){ System.out.println("remove(-1): " + e.getClass().getSimpleName()); }
        System.out.println("Unmodified list: " + list);
        elist = newList(list);
        System.out.println("empty list: " + elist);
        try{ elist.getFirst(); System.out.println("getFirst(): no exception"); }
        catch(RuntimeException e){ System.out.println("getFirst(): " + e.getClass().getSimpleName()); }
        try{ elist.getLast(); System.out.println("getLast(): no exception"); }
        catch(RuntimeException e){ System.out.println("getLast(): " + e.getClass().getSimpleName()); }
        try{ elist.removeFirst(); System.out.println("removeFirst(): no exception"); }
        catch(RuntimeException e){ System.out.println("removeFirst(): " + e.getClass().getSimpleName()); }
        try{ elist.iterator().next(); System.out.println("iterator().next(): no exception"); }
        catch(RuntimeException e){ System.out.println("iterator().next(): " + e.getClass().getSimpleName()); }

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: equals method with different lists");
        List<Integer> other = (list instanceof ArrayList) ? new LinkedList<>() : new ArrayList<>();
        iter = list.iterator();
        while(iter.hasNext())
            other.add(iter.next());
        System.out.println(list + ".equals(" + other + ") ? " + list.equals(other));
        other.set(0, -1);
        System.out.println(list + ".equals(" + other + ") ? " + list.equals(other));
        other = clone(list);
        other.removeLast();
        System.out.println(list + ".equals(" + other + ") ? " + list.equals(other));
        System.out.println(list + ".equals(" + list.reversed() + ") ? " + list.equals(list.reversed()));
        System.out.println(list + ".equals(\"" + list + "\") ? " + list.equals(list.toString()));
        elist = newList(list);
        System.out.println(elist + ".equals(" + newList(list) + ") ? " + elist.equals(newList(list)));

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: methods with duplicate elements");
        List<Integer> dlist = newList(list);
        dlist.add(7); dlist.add(3); dlist.add(7); dlist.add(9); dlist.add(3); dlist.add(7);
        System.out.println("list: " + dlist);
        System.out.println(dlist + ".indexOf(7) = " + dlist.indexOf(7));
        System.out.println(dlist + ".lastIndexOf(7) = " + dlist.lastIndexOf(7));
        System.out.println(dlist + ".indexOf(3) = " + dlist.indexOf(3));
        System.out.println(dlist + ".lastIndexOf(3) = " + dlist.lastIndexOf(3));
        i = 7;
        System.out.print(dlist + ".remove(7) = ");
        System.out.print(dlist.remove(i));
        System.out.println(" -> " + dlist);
        copylist = clone(dlist);
        nlist.clear(); nlist.add(7);
        System.out.print(copylist + ".removeAll(" + nlist + ") = ");
        copylist.removeAll(nlist);
        System.out.println(copylist);
        copylist = clone(dlist);
        nlist.clear(); nlist.add(3);
        System.out.print(copylist + ".retainAll(" + nlist + ") = ");
        copylist.retainAll(nlist);
        System.out.println(copylist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: sort method edge cases");
        elist = newList(list);
        elist.sort(null);
        System.out.println("empty list sorted: " + elist);
        elist.add(42);
        elist.sort(null);
        System.out.println("single element list sorted: " + elist);
        copylist = clone(list);
        copylist.sort(null);
        System.out.println("already sorted list sorted: " + copylist);
        copylist.sort(Comparator.reverseOrder());
        System.out.println("list sorted in reverse order: " + copylist);
        dlist.sort(null);
        System.out.println("list with duplicates sorted: " + dlist);

        System.out.println("\nTest case " + (++testNum) + " [" + list.getClass().getSimpleName() + "]: large number of elements");
        List<Integer> big = newList(list);
        for(int k=0; k<100; k++)
            big.add(k);
        for(int k=0; k<50; k++)
            big.addFirst(-k);
        System.out.println("size after adding 150 elements: " + big.size());
        System.out.println("getFirst() = " + big.getFirst() + ", getLast() = " + big.getLast() + ", get(75) = " + big.get(75));
        int sum = 0;
        for(Object o: big.toArray())
            sum += (Integer)o;
        System.out.println("sum of elements: " + sum);
        for(int k=0; k<140; k++)
            big.removeFirst();
        System.out.println("list after removing 140 elements: " + big);
    }
    public static List<Integer> newList(List<Integer> list){
        if(list instanceof ArrayList)
            return new ArrayList<>();
        else if (list instanceof LinkedList)
            return new LinkedList<>();
        else
            return null;
    }
    public static List<Integer> clone(List<Integer> list){
        if(list instanceof ArrayList)
            return (List<Integer>) ((ArrayList<Integer>)list).clone();
        else if (list instanceof LinkedList)
            return (List<Integer>) ((LinkedList<Integer>)list).clone();
        else
            return null;
    }
}