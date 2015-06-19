package hr.fer.oop.lab3.topic1;

import java.util.Iterator;


/**
 * 
 * @author Dominik
 * @version 2.0
 * 
 * class SimpleHashtable is used to store a number of values via hashcode allocation
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {
	TableEntry[] table;
	int size;
	//static SimpleHashtable duplicate;
	/**
	 * This Method returns a new iterator
	 */
	@Override
	public Iterator <SimpleHashtable.TableEntry> iterator() {
			    return new  myIterator();
			    }
	/**
	 * Class myIterator creates a new iterator for the SimpleHashtable
	 * @author Dominik
	 *
	 */
	public class myIterator implements Iterator <SimpleHashtable.TableEntry> {
		private int currentTable=0;
		private TableEntry current=table[0];
		private TableEntry temp=null;
		

		/**
		 * Method hasNext check whether there is another entry in the table
		 */
        @Override
        public boolean hasNext() {
				if (currentTable==table.length-1 && current==null) return false;
                else return true;
        }
        /**
         * Method next() returns the next entry in the table
         */
        @Override
        public SimpleHashtable.TableEntry next() {
                if (this.hasNext()){
                        while (current==null){
                                currentTable++;
                                current = table[currentTable];
                        }
                        temp = current;
                        current = current.getNext();
                        return temp;
                }
                return null;
        }
}


	public static void main(String[] args) {
		// create collection
		SimpleHashtable examMarks = new SimpleHashtable(2);  
		// fill data: 
		examMarks.put("Ivana", Integer.valueOf(2)); 
		examMarks.put("Ante", Integer.valueOf(2)); 
		examMarks.put("Jasna", Integer.valueOf(2)); 
		examMarks.put("Kristina", Integer.valueOf(5)); 
		examMarks.put("Ivana", Integer.valueOf(5)); // overwrites old grade for Ivana  
		// query collection: 
		Integer kristinaGrade =  (Integer)examMarks.get("Kristina"); System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5  
		// What is collection's size? Must be four! 
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		for	(Object	entry : examMarks) {
			for	(Object	entry2 : examMarks) {
				SimpleHashtable.TableEntry pair2 = (SimpleHashtable.TableEntry)entry2;
				System.out.printf("%s => %s%n",pair2.getKey(), pair2.getValue());
			}
			SimpleHashtable.TableEntry pair = (SimpleHashtable.TableEntry)entry;
			System.out.printf("%s => %s%n",pair.getKey(), pair.getValue());
		}
		
	}

	
	public SimpleHashtable() {
		table=new TableEntry[16];
		size=16;
	}
	
	public SimpleHashtable( int capacity) {
		int calc=calculateSize(capacity);
		 table= new TableEntry[calc];
		 size=calc;
	}

	/**
	 * Method calculateSize determines how large will the table be.
	 * @param input
	 * @return
	 */
	public int calculateSize(int input) {
		for (int i=0; i<input; i++){
			if (Math.pow(2,i)>input) return (int) Math.pow(2, i);
		}
		return input;
	}
	
	
	/**
	 * Method put places a new TableEntry input in the table in its slot.
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value) {
		try { key.equals(null);
		
		} catch (NullPointerException E) {
			System.err.println("Key can't be null!");
			System.exit(1);
		}
		try { value.equals(null);
		
		} catch (NullPointerException E) {
			System.err.println("Value can't be null!");
			System.exit(1);
		}
		
		int slot= Math.abs(key.hashCode()) % table.length;
		TableEntry current,previous = null;
		boolean exists=false;
		for (current=table[slot]; current!=null; current=current.next) {
			if (current.getKey().equals(key)) {
				current.setValue(value);
				exists=true; 
				break;
			}
			previous=current;
		}
		
		if (!exists) {
			TableEntry INput= new TableEntry(key, value, null);
			if (table[slot]==null) 
				table[slot]=INput;
			else previous.next=INput;
		}
	}
	/**
	 * Method get returns the value of a certain input key.
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		try { key.equals(null);
		
		} catch (NullPointerException E) {
			System.err.println("Key can't be null!");
			System.exit(2);
		}
	
		int slot= Math.abs(key.hashCode()) % size;
		TableEntry Output;
		for (Output = table[slot]; Output!=null; Output=Output.next) {
			if (Output.getKey().equals(key)) return Output.value;
		}
		return null;
			}
	
	/**
	 * Method size calculates how many inputs does the table have
	 * @return
	 */
	public int size() {
		int currentSlot, sizeCount=0;
		TableEntry currentTable;
		for (currentSlot=0; currentSlot<size; currentSlot++) {
			for (currentTable=table[currentSlot]; currentTable!=null; currentTable=currentTable.next) {
				sizeCount++;
			}
		}
		return sizeCount;
		}
	
	/**
	 * Method containsKey is self explanatory - it determines whether a key is located within the table
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {
		int slot= Math.abs(key.hashCode()) % size;
		TableEntry Search;
		for (Search=table[slot]; Search!=null; Search=Search.next) {
			if (Search.getKey().equals(key)) return true;
		}
		return false;
		}
	
	
	/**
	 * method remove removes an object from the table.
	 * @param key
	 */
	public void remove(Object key){
	try { key.equals(null);
		
		} catch (NullPointerException E) {
			System.err.println("Key can't be null!");
			System.exit(3);
		}
	
		int slot= Math.abs(key.hashCode()) % table.length;
		if (!containsKey(key)) { // Check if the key is present in the table
			return; 
		} 
		boolean removed=false;
		TableEntry current,previous=null;
		for (current=table[slot]; current!=null; current=current.next) {
			if (current.getKey().equals(key)) {
				if (previous==null) {
					table[slot]=current.next;
					removed=true;
					break;
				}
				previous.next=current.next;
				removed=true;
				break;
				}
		}
		if (removed) System.out.println("Succesfully removed");
		else System.out.println("Unsuccessfully removed");
	}
	
	/**
	 * method containsValue determines whether the table contains a certain value
	 * @param value
	 * @return
	 */
	public boolean containsValue(Object value){
		try { value.equals(null);

		} catch (NullPointerException E) {
			System.err.println("Value can't be null!");
			System.exit(1);
		}
		for (int i=0; i<calculateSize(size); i++) {
			if (table[i].value==value) return true; }
		return false;
	}	


	
	/**
	 * method isEmpty determines whether the table is empty or not
	 * @return
	 */

	public boolean isEmpty(){
		for (int i=0; i<size; i++) {
			if (table[i]!=null) return false; }
		return true;
	}
	
	/**
	 * method toString returns a string containing all of the table's values
	 * @return
	 */
	public String toString() {
		String s="";
		TableEntry currentTable;
		int currentSlot;
		for (currentSlot=0; currentSlot<size; currentSlot++) {
			for (currentTable=table[currentSlot]; currentTable!=null; currentTable=currentTable.next) {
				s+=currentTable.toString();
			}
		s+="\n";
		}
	return s;
	}
/**
 * Nested class TableEntry describes a single input for the SimpleHashtable.
 * @author Dominik
 *
 */
	public static class TableEntry {
		private Object key;
		private Object value;
		TableEntry next;
		
		public TableEntry(Object key, Object value, TableEntry object) {
		
			if(value.toString().compareTo("0")<=0) {
				throw  new NumberFormatException  ("Value can't be lesser than zero!") ;
			}			
			
			this.key=key;
			this.value=value;
			this.next=object;
		}
		public TableEntry getNext() {
			return this.next;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		
		public Object getKey() {
			return key;
		}
		@Override
		public String toString() {
			/*return "TableEntry [key=" + key + ", value=" + value + ", next="
					+ next + "]";*/
			return key+" "+value+" ";
		}
		
	}

	
}
